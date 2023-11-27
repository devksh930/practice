package me.devksh930.practice.like.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.devksh930.practice.like.domain.FeedLike;
import me.devksh930.practice.like.dto.FeedLikeRequest;
import me.devksh930.practice.like.repository.FeedCounterRepository;
import me.devksh930.practice.like.repository.FeedLikeRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Primary
@RequiredArgsConstructor
@Slf4j
public class BeforeFeedLikeService implements FeedLikeService {

    private final FeedLikeRepository feedLikeRepository;
    private final FeedCounterRepository feedCounterRepository;

    @Transactional
    @Override
    public void like(
        final Long feedId,
        final FeedLikeRequest request
    ) {
        final FeedLike create = FeedLike.builder()
            .userId(request.userId())
            .feedId(feedId)
            .build();
        //저장
        feedLikeRepository.save(create);
        //업데이트
        increaseFeed(feedId);
    }


    private void increaseFeed(final Long feedId) {
        int updateRows = feedCounterRepository.updateLikeIncrease(feedId);

        log.debug("updateRows {}", updateRows);
        if (updateRows != 1) {
            throw new RuntimeException("업데이트 실패");
        }
    }
}
