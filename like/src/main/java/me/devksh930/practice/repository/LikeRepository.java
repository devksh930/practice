package me.devksh930.practice.repository;

import me.devksh930.practice.domain.FeedLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<FeedLike, Long> {

    boolean existsByFeedIdAndUserId(final Long feedId, final Long UserId);
}