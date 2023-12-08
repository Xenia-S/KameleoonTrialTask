package com.example.kameleoontrialtask.controllers;

import com.example.kameleoontrialtask.dto.VoteRequest;
import com.example.kameleoontrialtask.services.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/vote")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @GetMapping("/get/chart/{quoteId}")
    public Map<LocalDateTime, Integer> getScoreChart(@PathVariable("quoteId") Long quoteId) {
        return voteService.getScoreChart(quoteId);
    }

    @PostMapping("/post")
    public void voteQuote(@RequestBody @Valid VoteRequest request) {
        voteService.voteQuote(request);
    }
}
