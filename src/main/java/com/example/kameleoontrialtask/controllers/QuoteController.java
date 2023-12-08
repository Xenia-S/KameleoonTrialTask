package com.example.kameleoontrialtask.controllers;

import com.example.kameleoontrialtask.dto.QuoteRequest;
import com.example.kameleoontrialtask.dto.QuoteResponse;
import com.example.kameleoontrialtask.services.QuoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quote")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    @PostMapping("/create")
    public QuoteResponse createQuote(@RequestBody @Valid QuoteRequest request) {
        return quoteService.createQuote(request);
    }

    @GetMapping("/get/random")
    public QuoteResponse getRandomQuote() {
        return quoteService.getRandomQuote();
    }

    @GetMapping("/get/last/{count}")
    public List<QuoteResponse> getLastQuotes(@PathVariable("count") int count) {
        return quoteService.getLastQuotes(count);
    }

    @GetMapping("/get/top")
    public List<QuoteResponse> getTopTenQuotes() {
        return quoteService.getTopTenQuotes();
    }

    @GetMapping("/get/flop")
    public List<QuoteResponse> getFlopTenQuotes() {
        return quoteService.getFlopTenQuotes();
    }

    @PostMapping("/update/{quoteId}")
    public QuoteResponse updateQuote(@PathVariable("quoteId") Long quoteId,
                                     @RequestBody @Valid QuoteRequest request) {
        return quoteService.updateQuote(quoteId, request);
    }

    @DeleteMapping("/delete/{quoteId}")
    public void deleteQuote(@PathVariable("quoteId") Long quoteId) {
        quoteService.deleteQuote(quoteId);
    }

}
