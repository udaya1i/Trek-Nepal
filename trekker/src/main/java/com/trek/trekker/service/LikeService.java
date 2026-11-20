package com.trek.trekker.service;

import com.trek.core.constants.LikeEntityType;
import com.trek.core.constants.NotificationEntityType;
import com.trek.core.entity.Comment;
import com.trek.core.entity.Like;
import com.trek.core.entity.TrekStory;
import com.trek.core.entity.TrekStrategy;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.repository.LikeRepository;
import com.trek.trekker.security.TrekkerSecurityContextHolderHolder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author udaya
 */
@Service
@AllArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final InAppNotificationService inAppNotificationService;
    private final TrekkerSecurityContextHolderHolder trekkerSecurityContextHolderHolder;

    public Long likeStory(TrekStory story) {
        inAppNotificationService.notifyLike(story.getWriter(), NotificationEntityType.STORY, story.getId());
        return like(story.getId(), LikeEntityType.STORY);
    }

    public Long likeStrategy(TrekStrategy strategy) {
        return like(strategy.getId(), LikeEntityType.STRATEGY);
    }

    public Long likeStrategyComment(Comment comment) {
        return like(comment.getId(), LikeEntityType.STRATEGY_COMMENT);
    }

    public Long likeStoryComment(Comment comment) {
         return like(comment.getId(), LikeEntityType.STORY_COMMENT);
    }

    private Long like(Long entityId, LikeEntityType entityType) {
        if (likeRepository.existsByEntityTypeAndEntityIdAndLikedByAndIsLikedTrue(entityType, entityId, trekkerSecurityContextHolderHolder.getCurrentUser())) {
            return likeRepository.countByEntityTypeAndEntityIdAndIsLikedTrue(entityType, entityId);
        }
        Like like = new Like();
        like.setLikedBy(trekkerSecurityContextHolderHolder.getCurrentUser());
        like.setEntityType(entityType);
        like.setEntityId(entityId);
        like.setIsLiked(Boolean.TRUE);
        likeRepository.save(like);
        return likeRepository.countByEntityTypeAndEntityIdAndIsLikedTrue(entityType, entityId);
    }

    public Long unLikeStory(TrekStory story) {
        return unLike(story.getId(), LikeEntityType.STORY);
    }

    public Long unLikeStrategy(TrekStrategy strategy) {
        return unLike(strategy.getId(), LikeEntityType.STRATEGY);
    }

    public Long unLikeStrategyComment(Comment comment) {
        return unLike(comment.getId(), LikeEntityType.STRATEGY_COMMENT);
    }

    public Long unLikeStoryComment(Comment comment) {
        return unLike(comment.getId(), LikeEntityType.STORY_COMMENT);
    }

    public Long unLike(Long entityId, LikeEntityType entityType) {
        if (likeRepository.existsByEntityTypeAndEntityIdAndLikedByAndIsLikedFalse(entityType, entityId, trekkerSecurityContextHolderHolder.getCurrentUser())) {
            return likeRepository.countByEntityTypeAndEntityIdAndIsLikedTrue(entityType, entityId);
        }
        Like like = likeRepository.findByEntityTypeAndEntityIdAndLikedBy(entityType, entityId, trekkerSecurityContextHolderHolder.getCurrentUser())
                .orElseThrow(() -> new RequestValidationException("Invalid comment"));
        like.setIsLiked(Boolean.FALSE);
        likeRepository.save(like);
        return likeRepository.countByEntityTypeAndEntityIdAndIsLikedTrue(entityType, entityId);
    }

    public Long count(Comment comment, LikeEntityType entityType) {
        return likeRepository.countByEntityTypeAndEntityIdAndIsLikedTrue(entityType, comment.getId());
    }


}
