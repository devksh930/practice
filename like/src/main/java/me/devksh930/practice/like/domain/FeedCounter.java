package me.devksh930.practice.like.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long likeCount;
    @Builder
    public FeedCounter(final Long id, final long likeCount) {
        this.id = id;
        this.likeCount = likeCount;
    }

    public void increaseCount() {
        this.likeCount += 1;
    }

    public void decreaseCount() {
        this.likeCount -= 1;
    }
}
