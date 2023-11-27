package me.devksh930.practice.like.repository;

import me.devksh930.practice.like.domain.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {

}
