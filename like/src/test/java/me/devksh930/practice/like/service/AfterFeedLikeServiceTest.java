package me.devksh930.practice.like.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import me.devksh930.practice.like.dto.FeedLikeRequest;
import me.devksh930.practice.like.repository.FeedCounterRepository;
import me.devksh930.practice.like.repository.FeedLikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AfterFeedLikeServiceTest {


    @Autowired
    private AfterFeedLikeService afterFeedLikeService;

    @Autowired
    private BeforeFeedLikeService beforeFeedLikeService;

    @Autowired
    private FeedCounterRepository feedCounterRepository;

    @Autowired
    private FeedLikeRepository feedLikeRepository;

    private final static int FEED_LIKE_COUNT = 3000;

    @BeforeEach
    void before() {
        feedLikeRepository.deleteAll();
        feedCounterRepository.resetCount(1L);

    }


    @Test
    @DisplayName("동시성 문제가 발생하는 좋아요 로직")
    void like() throws InterruptedException {

        final int treadCount = FEED_LIKE_COUNT;

        final ExecutorService executorService = Executors.newFixedThreadPool(200);
        final CountDownLatch countDownLatch = new CountDownLatch(treadCount);

        for (int i = 0; i < treadCount; i++) {
            int userId = i;
            executorService.submit(() -> {
                try {
                    afterFeedLikeService.like(1L, new FeedLikeRequest((long) userId + 1));
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        final Result result = getResult();
        assertEquals(FEED_LIKE_COUNT, result.feedLikePeopleCount());
        assertEquals(FEED_LIKE_COUNT, result.feedLikeCount());

    }

    @Test
    @DisplayName("동시성 문제가 발생하지 않는 로직")
    void like_concurrency() throws InterruptedException {

        final int treadCount = FEED_LIKE_COUNT;

        final ExecutorService executorService = Executors.newFixedThreadPool(200);
        final CountDownLatch countDownLatch = new CountDownLatch(treadCount);

        for (int i = 0; i < treadCount; i++) {
            int userId = i;
            executorService.submit(() -> {
                try {
                    beforeFeedLikeService.like(1L, new FeedLikeRequest((long) userId + 1));
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        final Result result = getResult();

        assertEquals(FEED_LIKE_COUNT, result.feedLikePeopleCount());
        assertEquals(FEED_LIKE_COUNT, result.feedLikeCount());
    }

    private Result getResult() {
        long feedLikePeopleCount = feedLikeRepository.findAll()
            .stream()
            .count();
        long feedLikeCount = feedCounterRepository.findByFeedId(1L).get().getLikeCount();
        return new Result(feedLikePeopleCount, feedLikeCount);
    }

    private record Result(long feedLikePeopleCount, long feedLikeCount) {

    }
}
