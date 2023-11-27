package me.devksh930.practice.like.controller;

import lombok.RequiredArgsConstructor;
import me.devksh930.practice.like.dto.FeedLikeRequest;
import me.devksh930.practice.like.service.FeedLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feeds/{feedId}/like")
@RequiredArgsConstructor
public class FeedLikeController {

    private final FeedLikeService feedLikeService;
    @PostMapping
    public ResponseEntity<Void> feedLike(
        @PathVariable("feedId") final Long feedId,
        @RequestBody final FeedLikeRequest request
    ) {
        feedLikeService.like(feedId, request);
        return ResponseEntity.noContent()
            .build();
    }
}
