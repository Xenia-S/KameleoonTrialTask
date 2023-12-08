package com.example.kameleoontrialtask.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteRequest {

    @NotNull
    private Long quoteId;

    @NotNull
    private Long userId;

    @Pattern(regexp = "^1|-1$", message = "Rating must be either 1 or -1")
    private String rating;
}
