package com.example.kameleoontrialtask.dto;

import com.example.kameleoontrialtask.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QuoteResponse {

    private Long id;
    private String content;
    private User postedBy;
    private int score;
    private LocalDateTime created;
    private LocalDateTime updated;

}
