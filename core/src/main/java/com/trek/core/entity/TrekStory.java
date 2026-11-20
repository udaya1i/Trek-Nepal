package com.trek.core.entity;

import com.trek.core.constants.MediaOwnerType;
import com.trek.core.constants.TrekStoryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table
public class TrekStory extends BaseEntity implements MediaOwner {

    @Column(name = "title")
    private String title;

    @Column(name = "slug", unique = true)
    private String slug;

    @Column(name = "views")
    private Long views;

    @Column(name = "short_description", length = 5000)
    private String shortDescription;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "completed_at")
    private LocalDate completedAt;

    @Column(name = "duration_days")
    private Integer durationDays;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "trek_story_highlights", joinColumns = @JoinColumn(name = "story_id"))
    @Column(name = "highlight", length = 2000)
    private List<String> highlights;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "trek_story_tags", joinColumns = @JoinColumn(name = "story_id"))
    @Column(name = "tag", length = 200)
    private List<String> tags;

    @ManyToOne
    private Trekker writer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_id")
    private Destination destination;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TrekStoryStatus status;

    @Override
    public MediaOwnerType getOwnerType() {
        return MediaOwnerType.TREKKER_STORY;
    }
}
