package com.trek.core.entity;

import com.trek.core.constants.CommentReplyStatus;
import jakarta.persistence.*;
import lombok.*;

/**
 * @author udaya
 */
@Entity
@Table(name = "comment_reply")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "comment")
public class CommentReply extends BaseEntity {

    @Column(name = "content", length = 5000)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reply_by", nullable = false)
    private Trekker replyBy;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CommentReplyStatus status;
}