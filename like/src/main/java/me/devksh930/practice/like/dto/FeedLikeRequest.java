package me.devksh930.practice.like.dto;

import jakarta.validation.constraints.NotBlank;

public record FeedLikeRequest(
    @NotBlank Long userId
) {


}
