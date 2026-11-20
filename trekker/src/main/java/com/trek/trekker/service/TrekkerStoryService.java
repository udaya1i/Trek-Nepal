package com.trek.trekker.service;

import com.trek.core.admin.service.MediaMappingService;
import com.trek.core.admin.service.SlugAssignmentService;
import com.trek.core.constants.*;
import com.trek.core.dto.request.FilterRequest;
import com.trek.core.dto.response.TrekStorySearchable;
import com.trek.core.entity.Comment;
import com.trek.core.entity.CommentReply;
import com.trek.core.entity.TrekStory;
import com.trek.core.exception.RequestValidationException;
import com.trek.core.repository.CommentReplyRepository;
import com.trek.core.repository.CommentRepository;
import com.trek.core.repository.TrekStoryRepository;
import com.trek.core.specification.FilterFieldToEntityFieldMapper;
import com.trek.core.trekker.specification.TrekStorySpecification;
import com.trek.core.util.PaginationUtil;
import com.trek.core.util.SlugUtil;
import com.trek.trekker.dto.command.TrekkerStoryAddCommand;
import com.trek.trekker.dto.command.TrekkerStoryEditCommand;
import com.trek.trekker.security.TrekkerSecurityContextHolderHolder;
import lombok.AllArgsConstructor;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author udaya
 */
@Service
@AllArgsConstructor
public class TrekkerStoryService {

    private static final double JARO_THRESHOLD = 0.70;
    private static final int JARO_LIMIT = 10;

    private final TrekStoryRepository trekStoryRepository;
    private final TrekkerSecurityContextHolderHolder loggedUserDetail;
    private final LikeService likeService;
    private final CommentRepository commentRepository;
    private final CommentReplyRepository commentReplyRepository;
    private final MediaMappingService mediaMappingService;
    private final InAppNotificationService inAppNotificationService;
    private final SlugAssignmentService slugAssignmentService;
    private final JaroWinklerSimilarity jaro = new JaroWinklerSimilarity();

    @Transactional
    public TrekStory create(TrekkerStoryAddCommand command) {
        TrekStory story = new TrekStory();
        story.setCompletedAt(command.getCompletedAt());
        story.setContent(command.getContent());
        story.setHighlights(command.getHighlights());
        story.setShortDescription(command.getShortDescription());
        story.setTitle(command.getTitle());
        story.setStatus(TrekStoryStatus.ACTIVE);
        story.setTags(command.getTags());
        story.setDurationDays(command.getDurationDays());
        story.setWriter(loggedUserDetail.getCurrentUser());
        story.setDestination(command.getDestination());
        story.setSlug(slugAssignmentService.ensureUniqueSlug(command.getTitle(), trekStoryRepository::existsBySlug));
        TrekStory save = trekStoryRepository.save(story);
        mediaMappingService.map(command.getImages(), save);
        return save;
    }

    @Transactional
    public TrekStory edit(TrekkerStoryEditCommand command) {
        TrekStory story = trekStoryRepository.findById(command.getId())
                .orElseThrow(() -> new RequestValidationException("Story not found"));

        if (!story.getWriter().getId().equals(loggedUserDetail.getCurrentUser().getId()))
            throw new RequestValidationException("You are not authorized to edit this story");

        story.setTitle(command.getTitle());
        story.setShortDescription(command.getShortDescription());
        story.setContent(command.getContent());
        story.setCompletedAt(command.getCompletedAt());
        story.setDurationDays(command.getDurationDays());
        story.setHighlights(command.getHighlights());
        story.setTags(command.getTags());
        story.setDestination(command.getDestination());
        story.setSlug(slugAssignmentService.resolveSlugOnUpdate(SluggableType.STORY, story.getId(),
                story.getSlug(), command.getSlug(), story.getTitle(), trekStoryRepository::existsBySlug));
        TrekStory save = trekStoryRepository.save(story);
        if (command.getImages() != null) {
            mediaMappingService.map(command.getImages(), save);
        }
        return save;
    }


    @Transactional(readOnly = true)
    public Page<TrekStory> getAll(FilterRequest filterRequest) {
        String keyword = filterRequest.getSearch();
        Pageable pageable = PaginationUtil.of(filterRequest.getPaginationRequest());

        var filters = FilterFieldToEntityFieldMapper.map(
                filterRequest.getFilterCriteriaRequests(), TrekStory.class);

        Page<TrekStory> likeResult = trekStoryRepository.findAll(
                TrekStorySpecification.searchSpec(keyword, filters), pageable);

        if (!likeResult.isEmpty() || keyword == null || keyword.isBlank()) {
            return likeResult;
        }
        return jaroFallback(keyword, pageable);
    }

    @Transactional(readOnly = true)
    public TrekStory getBySlug(String slug) {
        return trekStoryRepository.findBySlug(slug)
                .or(() -> SlugUtil.isNumeric(slug) ? trekStoryRepository.findById(Long.parseLong(slug)) : Optional.empty())
                .orElseThrow(() -> new RequestValidationException("Story not found"));
    }

    @Transactional
    public void incrementViews(String slug) {
        trekStoryRepository.incrementViews(getBySlug(slug).getId());
    }

    @Transactional
    public Long likeComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RequestValidationException("Comment not found"));
        if (CommentEntityType.STORY.equals(comment.getEntityType())) {
            TrekStory story = trekStoryRepository.findById(comment.getEntityId())
                    .orElseThrow(() -> new RequestValidationException("Story not found"));
            inAppNotificationService.notifyLike(story.getWriter(), NotificationEntityType.STORY_COMMENT, story.getId());
        }
        return likeService.likeStoryComment(comment);
    }

    @Transactional
    public Long likeStory(Long storyId) {
        TrekStory trekStory = trekStoryRepository.findById(storyId)
                .orElseThrow(() -> new RequestValidationException("Comment not found"));
        return likeService.likeStory(trekStory);
    }

    @Transactional
    public Long unLikeComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RequestValidationException("Comment not found"));
        return likeService.unLikeStoryComment(comment);
    }

    @Transactional
    public Long unLikeStory(Long storyId) {
        TrekStory comment = trekStoryRepository.findById(storyId)
                .orElseThrow(() -> new RequestValidationException("Comment not found"));
        return likeService.unLikeStory(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RequestValidationException("Comment not found"));
        if (loggedUserDetail.getCurrentUser().getId().equals(comment.getCommentBy().getId())) {
            comment.setStatus(CommentReplyStatus.DELETED);
        }
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteReply(Long replyId) {
        CommentReply reply = commentReplyRepository.findById(replyId)
                .orElseThrow(() -> new RequestValidationException("Comment not found"));
        if (loggedUserDetail.getCurrentUser().equals(reply.getReplyBy())) {
            reply.setStatus(CommentReplyStatus.DELETED);
        }
        commentReplyRepository.save(reply);
    }

    @Transactional
    public void deleteStory(Long storyId) {
        TrekStory story = trekStoryRepository.findById(storyId)
                .orElseThrow(() -> new RequestValidationException("Story not found"));
        story.setStatus(TrekStoryStatus.DELETED);
        trekStoryRepository.save(story);
    }


    private Page<TrekStory> jaroFallback(String keyword, Pageable pageable) {
        String kw = keyword.toLowerCase().trim();

        List<Long> matchedIds = trekStoryRepository.findAllSearchable().stream()
                .map(row -> scoreRow(row, kw))
                .filter(e -> e.getValue() >= JARO_THRESHOLD)
                .sorted(Comparator.comparingDouble(
                        Map.Entry<TrekStorySearchable, Double>::getValue).reversed())
                .limit(JARO_LIMIT)
                .map(e -> e.getKey().getId())
                .toList();

        if (matchedIds.isEmpty()) return Page.empty(pageable);

        List<TrekStory> stories = trekStoryRepository.findAllById(matchedIds);

        List<TrekStory> ordered = matchedIds.stream()
                .flatMap(id -> stories.stream().filter(s -> s.getId().equals(id)))
                .toList();

        return new PageImpl<>(ordered, pageable, ordered.size());
    }

    private Map.Entry<TrekStorySearchable, Double> scoreRow(TrekStorySearchable row, String kw) {
        double best = 0.0;
        best = Math.max(best, score(row.getTitle(), kw));
        best = Math.max(best, score(row.getWriterFullName(), kw));
        best = Math.max(best, score(row.getWriterUsername(), kw));
        best = Math.max(best, score(row.getDestinationName(), kw));
        best = Math.max(best, score(row.getDestinationRegion(), kw));
        return Map.entry(row, best);
    }

    private double score(String field, String kw) {
        if (field == null) return 0.0;
        return jaro.apply(kw, field.toLowerCase());
    }
}