package me.devksh930.practice.dto;

import jakarta.validation.constraints.NotBlank;

public record FeedLikeRequest(
    @NotBlank Long userId
) {


}
