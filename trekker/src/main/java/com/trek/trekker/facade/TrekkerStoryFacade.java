package com.trek.trekker.facade;

import com.trek.core.admin.service.DestinationQueryService;
import com.trek.core.admin.service.MediaMappingService;
import com.trek.core.constants.CommentEntityType;
import com.trek.core.constants.LikeEntityType;
import com.trek.core.constants.MediaOwnerType;
import com.trek.core.dto.request.FilterRequest;
import com.trek.core.dto.response.PaginatedResponse;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.entity.Comment;
import com.trek.core.entity.CommentReply;
import com.trek.core.entity.MediaMapping;
import com.trek.core.entity.TrekStory;
import com.trek.core.repository.CommentReplyRepository;
import com.trek.core.repository.CommentRepository;
import com.trek.core.repository.LikeRepository;
import com.trek.core.util.EntityCount;
import com.trek.trekker.dto.command.*;
import com.trek.trekker.dto.request.TrekkerStoryAddRequest;
import com.trek.trekker.dto.request.TrekkerStoryEditRequest;
import com.trek.trekker.dto.request.requestValidator.TrekStoryEditRequestValidator;
import com.trek.trekker.dto.request.requestValidator.TrekStoryRequestValidator;
import com.trek.trekker.dto.response.DestinationForStoryResponse;
import com.trek.trekker.dto.response.StoriesResponse;
import com.trek.trekker.dto.response.StoryDetailResponse;
import com.trek.trekker.security.TrekkerSecurityContextHolderHolder;
import com.trek.trekker.service.CommentService;
import com.trek.trekker.service.TrekkerStoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author udaya
 */
//TODO: Properly segregate validation logic into validator
@Slf4j
@Service
@AllArgsConstructor
public class TrekkerStoryFacade {

    private final TrekkerStoryService trekkerStoryService;
    private final TrekStoryRequestValidator trekStoryRequestValidator;
    private final CommentService commentService;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final TrekkerSecurityContextHolderHolder trekkerSecurityContextHolderHolder;
    private final CommentReplyRepository commentReplyRepository;
    private final TrekStoryEditRequestValidator editRequestValidator;
    private final MediaMappingService mediaMappingService;
    private final DestinationQueryService destinationQueryService;

    public ServerResponse<Void> add(TrekkerStoryAddRequest request) {
        TrekkerStoryAddCommand command = trekStoryRequestValidator.validate(request);
        trekkerStoryService.create(command);
        return ServerResponse.<Void>builder()
                .status(HttpStatus.CREATED)
                .message("Story shared successfully")
                .build();
    }

    public ServerResponse<List<DestinationForStoryResponse>> getAllDestination() {
         return ServerResponse.<List<DestinationForStoryResponse>>builder()
                .status(HttpStatus.OK)
                .data(DestinationForStoryResponse.from(destinationQueryService.getAll()))
                .message("Destination(s) fetched successfully")
                .build();
    }

    public ServerResponse<Void> edit(TrekkerStoryEditRequest request) {
        TrekkerStoryEditCommand command = editRequestValidator.validate(request);
        trekkerStoryService.edit(command);
        return ServerResponse.<Void>builder()
                .status(HttpStatus.OK)
                .message("Story updated successfully")
                .build();
    }


    @Transactional
    public PaginatedResponse<StoriesResponse> getAll(FilterRequest filterRequest) {

        Page<TrekStory> storyPage = trekkerStoryService.getAll(filterRequest);

        List<Long> storyIds = storyPage.getContent()
                .stream()
                .map(TrekStory::getId)
                .toList();

        Map<Long, List<MediaMapping>> collect = mediaMappingService.getAllImage(storyIds, MediaOwnerType.TREKKER_STORY).stream()
                .collect(Collectors.groupingBy(MediaMapping::getOwnerId));

        Map<Long, Long> commentCountMap;
        Map<Long, Long> likeCountMap;

        if (!storyIds.isEmpty()) {

            commentCountMap = commentRepository
                    .countByEntityTypeAndEntityIdIn(CommentEntityType.STORY, storyIds)
                    .stream()
                    .collect(Collectors.toMap(
                            EntityCount::getEntityId,
                            EntityCount::getCount
                    ));

            likeCountMap = likeRepository
                    .countLikesByEntityTypeAndEntityIdIn(LikeEntityType.STORY.name(), storyIds)
                    .stream()
                    .collect(Collectors.toMap(
                            EntityCount::getEntityId,
                            EntityCount::getCount
                    ));
        } else {
            likeCountMap = Collections.emptyMap();
            commentCountMap = Collections.emptyMap();
        }

        List<StoriesResponse> content = storyPage.getContent().stream()
                .map(story -> {
                    long commentCount = commentCountMap.getOrDefault(story.getId(), 0L);
                    long likeCount = likeCountMap.getOrDefault(story.getId(), 0L);
                    return StoriesResponse.from(story, likeCount, commentCount, collect.getOrDefault(story.getId(), List.of()));
                })
                .toList();

        Page<StoriesResponse> responsePage =
                new PageImpl<>(content, storyPage.getPageable(), storyPage.getTotalElements());

        return PaginatedResponse
                .data(responsePage)
                .status(HttpStatus.OK)
                .message("Story(s) fetched")
                .build();
    }

    public ServerResponse<StoryDetailResponse> getBySlug(String slug) {
        TrekStory story = trekkerStoryService.getBySlug(slug);
        Long storyId = story.getId();

        Map<Long, List<MediaMapping>> collect = mediaMappingService.getAllImage(List.of(storyId), MediaOwnerType.TREKKER_STORY)
                .stream().collect(Collectors.groupingBy(MediaMapping::getOwnerId));

        return ServerResponse.<StoryDetailResponse>builder()
                .status(HttpStatus.OK)
                .data(StoryDetailResponse.from(story,
                        trekkerSecurityContextHolderHolder.getCurrentUser() != null
                                ? likeRepository.getLikeResponse(LikeEntityType.STORY, storyId, trekkerSecurityContextHolderHolder.getCurrentUser().getId()) :
                                likeRepository.getLikeResponse(LikeEntityType.STORY, storyId), collect.getOrDefault(story.getId(), List.of()), null))
                .message("Story detail fetched")
                .build();
    }

    public ServerResponse<CommentResponse> comment(CommentRequest request) {
        Comment comment = commentService.comment(request);
        return ServerResponse.<CommentResponse>builder()
                .status(HttpStatus.CREATED)
                .data(CommentResponse.from(comment, likeRepository.getLikeResponse(LikeEntityType.STORY_COMMENT, comment.getId(),
                        trekkerSecurityContextHolderHolder.getCurrentUser().getId()), commentReplyRepository.countByCommentId(comment.getId())))
                .message("Commented successfully")
                .build();
    }

    @Transactional
    public PaginatedResponse<CommentResponse> getAllComment(FilterRequest request, Long storyId) {
        Page<Comment> comments = commentService.getAllStoryComment(request, storyId);
        if (trekkerSecurityContextHolderHolder.getCurrentUser() == null) {
            return PaginatedResponse.data(comments.map(comment -> CommentResponse.from(comment, likeRepository.countByEntityTypeAndEntityIdAndIsLikedTrue(LikeEntityType.STORY_COMMENT, comment.getId())
                            , commentReplyRepository.countByCommentId(comment.getId()))))
                    .message("Story comments fetched")
                    .status(HttpStatus.OK)
                    .build();
        }
        return PaginatedResponse.data(comments.map(comment -> CommentResponse.from(comment, likeRepository.getLikeResponse(LikeEntityType.STORY_COMMENT, comment.getId(), trekkerSecurityContextHolderHolder.getCurrentUser().getId()),
                        commentReplyRepository.countByCommentId(comment.getId()))))
                .message("Story comments fetched")
                .status(HttpStatus.OK)
                .build();
    }

    public PaginatedResponse<CommentResponse.ReplyResponse> getReply(FilterRequest filterRequest, Long commentId) {
        Page<CommentReply> replies = commentService.getAllReplies(filterRequest, commentId);
        return PaginatedResponse
                .data(replies.map(CommentResponse.ReplyResponse::from))
                .status(HttpStatus.OK)
                .message("Replies fetched")
                .build();
    }

    public ServerResponse<CommentResponse.ReplyResponse> reply(ReplyRequest request) {
        CommentReply reply = commentService.reply(request);
        return ServerResponse.<CommentResponse.ReplyResponse>builder()
                .status(HttpStatus.CREATED)
                .data(CommentResponse.ReplyResponse.from(reply))
                .message("Replied successfully")
                .build();
    }

    public ServerResponse<Map<String, Long>> likeComment(Long storyId) {
        Long count = trekkerStoryService.likeComment(storyId);
        return ServerResponse.<Map<String, Long>>builder()
                .status(HttpStatus.OK)
                .data(Map.of("count", count))
                .message("Comment liked")
                .build();
    }

    public ServerResponse<Map<String, Long>> likeStory(Long storyId) {
        Long count = trekkerStoryService.likeStory(storyId);
        return ServerResponse.<Map<String, Long>>builder()
                .status(HttpStatus.OK)
                .data(Map.of("count", count))
                .message("Story liked")
                .build();
    }

    public ServerResponse<Map<String, Long>> unLikeComment(Long commentId) {
        Long count = trekkerStoryService.unLikeComment(commentId);
        return ServerResponse.<Map<String, Long>>builder()
                .status(HttpStatus.OK)
                .data(Map.of("count", count))
                .message("Comment unliked")
                .build();
    }

    public ServerResponse<Map<String, Long>> unLikeStory(Long commentId) {
        Long count = trekkerStoryService.unLikeStory(commentId);
        return ServerResponse.<Map<String, Long>>builder()
                .status(HttpStatus.OK)
                .data(Map.of("count", count))
                .message("Story unliked")
                .build();
    }

    public ServerResponse<Void> deleteComment(Long commentId) {
        trekkerStoryService.deleteComment(commentId);
        return ServerResponse.<Void>builder()
                .status(HttpStatus.OK)
                .message("Comment deleted successfully")
                .build();
    }

    public ServerResponse<Void> deleteStory(Long storyId) {
        trekkerStoryService.deleteStory(storyId);
        return ServerResponse.<Void>builder()
                .status(HttpStatus.OK)
                .message("Story deleted successfully")
                .build();
    }

    public ServerResponse<Void> deleteReply(Long replyId) {
        trekkerStoryService.deleteReply(replyId);
        return ServerResponse.<Void>builder()
                .status(HttpStatus.OK)
                .message("Reply deleted successfully")
                .build();
    }

    public ServerResponse<Void> recordView(String slug) {
        trekkerStoryService.incrementViews(slug);
        return ServerResponse.<Void>builder()
                .status(HttpStatus.OK)
                .message("View recorded successfully")
                .build();
    }


}