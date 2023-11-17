package me.devksh930.practice.service;

import lombok.RequiredArgsConstructor;
import me.devksh930.practice.domain.Feed;
import me.devksh930.practice.domain.FeedCounter;
import me.devksh930.practice.dto.FeedCreateRequest;
import me.devksh930.practice.repository.FeedRepository;
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
