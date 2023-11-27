package me.devksh930.practice.like.repository;

import me.devksh930.practice.like.domain.FeedLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedLikeRepository extends JpaRepository<FeedLike, Long> {

    boolean existsByFeedIdAndUserId(final Long feedId, final Long UserId);
}