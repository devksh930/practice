package me.devksh930.practice.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import me.devksh930.practice.dto.FeedLikeRequest;
import me.devksh930.practice.repository.FeedRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AfterFeedLikeServiceTest {

    @Autowired
    private FeedRepository feedRepository;

    @Autowired
    private AfterFeedLikeService afterFeedLikeService;

    @Test
    @DisplayName("")
    void like() throws InterruptedException {

        final int treadCount = 100;

        final ExecutorService executorService = Executors.newFixedThreadPool(32);
        final CountDownLatch countDownLatch = new CountDownLatch(treadCount);

        for (int i = 0; i < treadCount; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    afterFeedLikeService.like(1L, new FeedLikeRequest((long) finalI));
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
    }
}