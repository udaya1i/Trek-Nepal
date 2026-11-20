package com.trek.trekker.service;

import com.trek.core.admin.service.TrekStrategyQueryService;
import com.trek.core.constants.CommentEntityType;
import com.trek.core.constants.CommentReplyStatus;
import com.trek.core.constants.NotificationEntityType;
import com.trek.core.dto.request.FilterRequest;
import com.trek.core.entity.Comment;
import com.trek.core.entity.CommentReply;
import com.trek.core.entity.TrekStory;
import com.trek.core.entity.TrekStrategy;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.repository.CommentReplyRepository;
import com.trek.core.repository.CommentRepository;
import com.trek.core.repository.TrekStoryRepository;
import com.trek.core.specification.FilterFieldToEntityFieldMapper;
import com.trek.core.trekker.specification.CommentSpecification;
import com.trek.core.trekker.specification.ReplySpecification;
import com.trek.core.util.PaginationUtil;
import com.trek.trekker.dto.command.CommentRequest;
import com.trek.trekker.dto.command.ReplyRequest;
import com.trek.trekker.security.TrekkerSecurityContextHolderHolder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author udaya
 */
@Service
@AllArgsConstructor
public class CommentService {

    private final TrekkerSecurityContextHolderHolder trekkerSecurityContextHolder;
    private final CommentRepository commentRepository;
    private final CommentReplyRepository commentReplyRepository;
    private final TrekStrategyQueryService strategyQueryService;
    private final LikeService likeService;
    private final InAppNotificationService inAppNotificationService;
    private final TrekStoryRepository storyRepository;

    @Transactional
    public Comment comment(CommentRequest request) {
        if (CommentEntityType.STRATEGY.name().equals(request.getEntityType())) {
            strategyQueryService.getById(request.getEntityId());
            Comment comment = buildComment(CommentEntityType.valueOf(request.getEntityType()));
            comment.setEntityId(request.getEntityId());
            comment.setContent(request.getComment());
            comment.setStatus(CommentReplyStatus.ACTIVE);
            return commentRepository.save(comment);
        } else if (CommentEntityType.STORY.name().equals(request.getEntityType())) {
            TrekStory story = storyRepository.findById(request.getEntityId()).orElseThrow(() -> new RequestValidationException("Invalid story"));
            Comment comment = buildComment(CommentEntityType.valueOf(request.getEntityType()));
            comment.setEntityId(request.getEntityId());
            comment.setContent(request.getComment());
            comment.setStatus(CommentReplyStatus.ACTIVE);
            inAppNotificationService.notifyComment(story.getWriter(), NotificationEntityType.STORY_COMMENT, story.getId());
            return commentRepository.save(comment);
        }
        throw new RequestValidationException("Invalid entity type");
    }

    private Comment buildComment(CommentEntityType entityType) {
        Comment comment = new Comment();
        comment.setEntityType(entityType);
        comment.setCommentBy(trekkerSecurityContextHolder.getCurrentUser());
        return comment;
    }

    @Transactional
    public CommentReply reply(ReplyRequest request) {
        Comment comment = commentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new RequestValidationException("Invalid comment"));
        CommentReply reply = new CommentReply();
        TrekStory story = storyRepository.findById(comment.getEntityId())
                .orElseThrow(() -> new RequestValidationException("Internal Server Error"));
        reply.setContent(request.getReply());
        reply.setStatus(CommentReplyStatus.ACTIVE);
        reply.setReplyBy(trekkerSecurityContextHolder.getCurrentUser());
        reply.setComment(comment);
        CommentReply save = commentReplyRepository.save(reply);
        inAppNotificationService.notifyReply(story.getWriter(), save.getId());
        return save;
    }

    @Transactional(readOnly = true)
    public Page<Comment> getAllStoryComment(FilterRequest request, Long storyId) {
        storyRepository.findById(storyId).orElseThrow(() -> new RequestValidationException("Invalid story"));
        return commentRepository.findAll(CommentSpecification
                        .searchSpec(FilterFieldToEntityFieldMapper
                                .map(request.getFilterCriteriaRequests(), Comment.class), storyId, CommentEntityType.STORY),
                PaginationUtil.of(request.getPaginationRequest()));
    }

    @Transactional(readOnly = true)
    public Page<Comment> getAllStrategyComment(FilterRequest request, Long strategyId) {
        strategyQueryService.getById(strategyId);
        return commentRepository.findAll(CommentSpecification
                        .searchSpec(FilterFieldToEntityFieldMapper
                                .map(request.getFilterCriteriaRequests(), Comment.class), strategyId, CommentEntityType.STRATEGY),
                PaginationUtil.of(request.getPaginationRequest()));
    }

    @Transactional(readOnly = true)
    public Page<CommentReply> getAllReplies(FilterRequest request, Long commentId) {
        commentRepository.findById(commentId)
                .orElseThrow(() -> new RequestValidationException("Invalid comment"));

        return commentReplyRepository.findAll(ReplySpecification.searchSpec(FilterFieldToEntityFieldMapper
                .map(request.getFilterCriteriaRequests(), CommentReply.class), commentId), PaginationUtil.of(request.getPaginationRequest()));
    }

    @Transactional
    public Long likeStrategyComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RequestValidationException("Comment not found"));
        return likeService.likeStrategyComment(comment);
    }

    @Transactional
    public Long likeStrategy(Long strategyId) {
        TrekStrategy strategy = strategyQueryService.getById(strategyId);
        return likeService.likeStrategy(strategy);
    }

    @Transactional
    public Long unLikeStrategyComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RequestValidationException("Comment not found"));
        return likeService.unLikeStrategyComment(comment);
    }

    @Transactional
    public Long unLikeStrategy(Long strategyId) {
        TrekStrategy strategy = strategyQueryService.getById(strategyId);
        return likeService.unLikeStrategy(strategy);
    }


}
