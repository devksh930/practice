package me.devksh930.practice.like.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String contents;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "feed_counter_id")
    private FeedCounter feedCounter;

    @Builder
    public Feed(
        final Long id,
        final String title,
        final String contents,
        final FeedCounter feedCounter
    ) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.feedCounter = feedCounter;
    }
}