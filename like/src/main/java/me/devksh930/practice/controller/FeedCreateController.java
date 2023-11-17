package me.devksh930.practice.controller;

import java.net.URI;
import me.devksh930.practice.dto.FeedCreateRequest;
import me.devksh930.practice.service.FeedCreateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feeds")
public class FeedCreateController {

    private final FeedCreateService feedCreateService;

    public FeedCreateController(final FeedCreateService feedCreateService) {
        this.feedCreateService = feedCreateService;
    }

    @PostMapping
    public ResponseEntity<Void> create(
        @RequestBody final FeedCreateRequest request
    ) {
        final Long feedId = feedCreateService.create(request);

        return ResponseEntity.created(URI.create("/feed/" + feedId))
            .build();
    }
}
