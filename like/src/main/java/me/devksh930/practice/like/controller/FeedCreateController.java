package me.devksh930.practice.like.controller;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import me.devksh930.practice.like.dto.FeedCreateRequest;
import me.devksh930.practice.like.service.FeedCreateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedCreateController {

    private final FeedCreateService feedCreateService;

    @PostMapping
    public ResponseEntity<Void> create(
        @RequestBody final FeedCreateRequest request
    ) {
        final Long feedId = feedCreateService.create(request);

        return ResponseEntity.created(URI.create("/feeds/" + feedId))
            .build();
    }
}
