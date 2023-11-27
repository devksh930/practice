package me.devksh930.practice.like.service;

import me.devksh930.practice.like.dto.FeedLikeRequest;

public interface FeedLikeService {

    void like(final Long feedId, final FeedLikeRequest request);
}
