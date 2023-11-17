package me.devksh930.practice.service;

import lombok.RequiredArgsConstructor;
import me.devksh930.practice.domain.FeedCounter;
import me.devksh930.practice.domain.FeedLike;
import me.devksh930.practice.dto.FeedLikeRequest;
import me.devksh930.practice.repository.FeedCounterRepository;
import me.devksh930.practice.repository.FeedLikeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//@Primary
@RequiredArgsConstructor
public class AfterFeedLikeService implements FeedLikeService {

    private final FeedCounterRepository feedCounterRepository;
    private final FeedLikeRepository feedLikeRepository;

    @Transactional
    @Override
    public void like(final Long feedId, final FeedLikeRequest request) {

        likeDuplicateCheck(feedId, request);

        final FeedLike create = FeedLike.builder()
            .userId(request.userId())
            .feedId(feedId)
            .build();
        feedLikeRepository.save(create);

        final FeedCounter feedCounter = feedCounterRepository.findByFeedId(feedId)
            .orElseThrow();

        feedCounter.increaseCount();
    }

    private void likeDuplicateCheck(final Long feedId, final FeedLikeRequest request) {
        if (feedLikeRepository.existsByFeedIdAndUserId(feedId, request.userId())) {
            throw new RuntimeException("이미 좋아요한 게시물");
        }
    }

}
