package com.trek.core.entity;

import com.trek.core.constants.CommentEntityType;
import com.trek.core.constants.CommentReplyStatus;
 import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author udaya
 */
@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity  {

    @Column(name = "content", length = 5000)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comment_by", nullable = false)
    private Trekker commentBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type", nullable = false)
    private CommentEntityType entityType;

    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CommentReplyStatus status;

}