package me.devksh930.practice.like.service;

import lombok.RequiredArgsConstructor;
import me.devksh930.practice.like.domain.Feed;
import me.devksh930.practice.like.domain.FeedCounter;
import me.devksh930.practice.like.dto.FeedCreateRequest;
import me.devksh930.practice.like.repository.FeedRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedCreateService {

    private final FeedRepository feedRepository;
    public Long create(final FeedCreateRequest request) {

        final Feed create = Feed.builder()
            .title(request.title())
            .contents(request.contents())
            .feedCounter(FeedCounter.builder().likeCount(0).build())
            .build();

        return feedRepository.save(create).getId();
    }
}
