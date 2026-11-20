package com.trek.trekker.controller;

import com.trek.core.constants.PublicPathConstant;
import com.trek.core.dto.request.FilterRequest;
import com.trek.core.dto.response.PaginatedResponse;
import com.trek.core.dto.response.ServerResponse;
import com.trek.core.security.constant.Authorities;
import com.trek.trekker.dto.command.CommentRequest;
import com.trek.trekker.dto.command.CommentResponse;
import com.trek.trekker.dto.command.ReplyRequest;
import com.trek.trekker.dto.request.TrekkerStoryAddRequest;
import com.trek.trekker.dto.request.TrekkerStoryEditRequest;
import com.trek.trekker.dto.response.DestinationForStoryResponse;
import com.trek.trekker.dto.response.StoriesResponse;
import com.trek.trekker.dto.response.StoryDetailResponse;
import com.trek.trekker.facade.TrekkerStoryFacade;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(PublicPathConstant.BASE_URL + PublicPathConstant.STORY)
@AllArgsConstructor
public class TrekkerStoryController {

    private final TrekkerStoryFacade trekkerStoryFacade;

    @GetMapping(PublicPathConstant.DESTINATIONS)
    public ResponseEntity<ServerResponse<List<DestinationForStoryResponse>>> getAllDestination() {
        ServerResponse<List<DestinationForStoryResponse>> serverResponse = trekkerStoryFacade.getAllDestination();
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.TREKKER_STORY)
    @PostMapping(PublicPathConstant.ADD)
    public ResponseEntity<ServerResponse<Void>> add(@Valid @RequestBody TrekkerStoryAddRequest request) {
        ServerResponse<Void> serverResponse = trekkerStoryFacade.add(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.TREKKER_STORY)
    @PostMapping(PublicPathConstant.ID + PublicPathConstant.EDIT)
    public ResponseEntity<ServerResponse<Void>> edit(@PathVariable("id") Long storyId, @Valid @RequestBody TrekkerStoryEditRequest request) {
        request.setId(storyId);
        ServerResponse<Void> serverResponse = trekkerStoryFacade.edit(request);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PostMapping
    public ResponseEntity<PaginatedResponse<StoriesResponse>> getAll(@RequestBody FilterRequest filterRequest) {
        PaginatedResponse<StoriesResponse> serverResponse = trekkerStoryFacade.getAll(filterRequest);
        return ResponseEntity.status(serverResponse.getStatus()).body(serverResponse);
    }

    @GetMapping(PublicPathConstant.SLUG)
    public ResponseEntity<ServerResponse<StoryDetailResponse>> getBySlug(@PathVariable("slug") String slug) {
        ServerResponse<StoryDetailResponse> serverResponse = trekkerStoryFacade.getBySlug(slug);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PostMapping(PublicPathConstant.SLUG + PublicPathConstant.VIEW)
    public ResponseEntity<ServerResponse<Void>> recordView(@PathVariable("slug") String slug) {
        ServerResponse<Void> serverResponse = trekkerStoryFacade.recordView(slug);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.TREKKER_STORY)
    @PostMapping(PublicPathConstant.COMMENT)
    public ResponseEntity<ServerResponse<CommentResponse>> comment(@RequestBody CommentRequest commentRequest) {
        ServerResponse<CommentResponse> serverResponse = trekkerStoryFacade.comment(commentRequest);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PostMapping(PublicPathConstant.ID + PublicPathConstant.COMMENT)
    public ResponseEntity<PaginatedResponse<CommentResponse>> getAllComment(@RequestBody FilterRequest filterRequest, @PathVariable("id") Long storyId) {
        PaginatedResponse<CommentResponse> serverResponse = trekkerStoryFacade.getAllComment(filterRequest, storyId);
        return ResponseEntity.status(serverResponse.getStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.TREKKER_STORY)
    @PostMapping(PublicPathConstant.COMMENT + PublicPathConstant.ID + PublicPathConstant.REPLY)
    public ResponseEntity<ServerResponse<CommentResponse.ReplyResponse>> reply(@PathVariable("id") Long storyId, @RequestBody ReplyRequest replyRequest) {
        ServerResponse<CommentResponse.ReplyResponse> serverResponse = trekkerStoryFacade.reply(replyRequest);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PostMapping(PublicPathConstant.COMMENT + PublicPathConstant.ID + PublicPathConstant.GET_REPLIES)
    public ResponseEntity<PaginatedResponse<CommentResponse.ReplyResponse>> getAllReplies(@PathVariable("id") Long commentId, @RequestBody FilterRequest filterRequest) {
        PaginatedResponse<CommentResponse.ReplyResponse> reply = trekkerStoryFacade.getReply(filterRequest, commentId);
        return ResponseEntity.status(reply.getStatus()).body(reply);
    }

    @PreAuthorize(Authorities.TREKKER_STORY)
    @PostMapping(PublicPathConstant.ID + PublicPathConstant.LIKE)
    public ResponseEntity<ServerResponse<Map<String, Long>>> likeStory(@PathVariable("id") Long storyId) {
        ServerResponse<Map<String, Long>> serverResponse = trekkerStoryFacade.likeStory(storyId);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.TREKKER_STORY)
    @PostMapping(PublicPathConstant.COMMENT + PublicPathConstant.ID + PublicPathConstant.LIKE)
    public ResponseEntity<ServerResponse<Map<String, Long>>> likeComment(@PathVariable("id") Long storyId) {
        ServerResponse<Map<String, Long>> serverResponse = trekkerStoryFacade.likeComment(storyId);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.TREKKER_STORY)
    @PostMapping(PublicPathConstant.ID + PublicPathConstant.UNLIKE)
    public ResponseEntity<ServerResponse<Map<String, Long>>> unLikeStory(@PathVariable("id") Long storyId) {
        ServerResponse<Map<String, Long>> serverResponse = trekkerStoryFacade.unLikeStory(storyId);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.TREKKER_STORY)
    @PostMapping(PublicPathConstant.COMMENT + PublicPathConstant.ID + PublicPathConstant.UNLIKE)
    public ResponseEntity<ServerResponse<Map<String, Long>>> unLikeComment(@PathVariable("id") Long storyId) {
        ServerResponse<Map<String, Long>> serverResponse = trekkerStoryFacade.unLikeComment(storyId);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PostMapping(PublicPathConstant.COMMENT + PublicPathConstant.ID + PublicPathConstant.DELETE)
    @PreAuthorize(Authorities.TREKKER_STORY)
    public ResponseEntity<ServerResponse<Void>> deleteComment(@PathVariable("id") Long storyId) {
        ServerResponse<Void> serverResponse = trekkerStoryFacade.deleteComment(storyId);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.TREKKER_STORY)
    @PostMapping(PublicPathConstant.COMMENT + PublicPathConstant.REPLY + PublicPathConstant.ID + PublicPathConstant.DELETE)
    public ResponseEntity<ServerResponse<Void>> deleteReply(@PathVariable("id") Long storyId) {
        ServerResponse<Void> serverResponse = trekkerStoryFacade.deleteReply(storyId);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }

    @PreAuthorize(Authorities.TREKKER_STORY)
    @PostMapping(PublicPathConstant.ID + PublicPathConstant.DELETE)
    public ResponseEntity<ServerResponse<Void>> deleteStory(@PathVariable("id") Long storyId) {
        ServerResponse<Void> serverResponse = trekkerStoryFacade.deleteStory(storyId);
        return ResponseEntity.status(serverResponse.getHttpStatus()).body(serverResponse);
    }


}