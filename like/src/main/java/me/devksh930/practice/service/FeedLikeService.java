package me.devksh930.practice.service;

import me.devksh930.practice.dto.FeedLikeRequest;

public interface FeedLikeService {

    void like(final Long feedId, final FeedLikeRequest request);
}
