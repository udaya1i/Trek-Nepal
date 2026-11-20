package com.trek.trekker.dto.command;

import com.trek.core.dto.response.LikeResponse;
import com.trek.core.dto.response.UserResponse;
import com.trek.core.entity.Comment;
import com.trek.core.entity.CommentReply;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author udaya
 */
@Data
public class CommentResponse {

    private Long id;
    private UserResponse commentBy;
    private String comment;
    private LocalDateTime commentAt;
    private LikeResponse likeCount;
    private Long repliesCount;


    public static CommentResponse from(Comment comment, LikeResponse count, Long repliesCount) {
        CommentResponse response = new CommentResponse();
        response.setCommentBy(UserResponse.from(comment.getCommentBy()));
        response.setComment(comment.getContent());
        response.setId(comment.getId());
        response.setLikeCount(count);
        response.setRepliesCount(repliesCount);
        response.setCommentAt(comment.getCreatedAt());
        return response;
    }

    public static CommentResponse from(Comment comment, Long count, Long repliesCount) {
        CommentResponse response = new CommentResponse();
        response.setCommentBy(UserResponse.from(comment.getCommentBy()));
        response.setComment(comment.getContent());
        response.setId(comment.getId());
        response.setRepliesCount(repliesCount);
        response.setLikeCount(new LikeResponse(count, false));
        response.setCommentAt(comment.getCreatedAt());
        return response;
    }

    @Data
    public static class ReplyResponse {
        private UserResponse replyBy;
        private String reply;
        private LocalDateTime replyAt;
        private Long id;

        public static ReplyResponse from(CommentReply replyMap) {
            ReplyResponse response = new ReplyResponse();
            response.setId(replyMap.getId());
            response.setReply(replyMap.getContent());
            response.setReplyBy(UserResponse.from(replyMap.getReplyBy()));
            response.setReplyAt(replyMap.getCreatedAt());
            return response;
        }
    }

}
