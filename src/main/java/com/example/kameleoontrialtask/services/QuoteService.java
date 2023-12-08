package com.example.kameleoontrialtask.services;

import com.example.kameleoontrialtask.dto.QuoteRequest;
import com.example.kameleoontrialtask.dto.QuoteResponse;
import com.example.kameleoontrialtask.entities.Quote;
import com.example.kameleoontrialtask.entities.User;
import com.example.kameleoontrialtask.entities.Vote;
import com.example.kameleoontrialtask.repositories.QuoteRepo;
import com.example.kameleoontrialtask.repositories.UserRepo;
import com.example.kameleoontrialtask.repositories.VoteRepo;
import com.example.kameleoontrialtask.util.QuoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.example.kameleoontrialtask.util.Messages.*;
import static com.example.kameleoontrialtask.util.QuoteMapper.quoteToQuoteResponse;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final UserRepo userRepo;
    private final QuoteRepo quoteRepo;
    private final VoteRepo voteRepo;

    public QuoteResponse createQuote(QuoteRequest request) {
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new NullPointerException(USER_NOT_FOUND.getMessage()));

        Quote quote = Quote.builder()
                .content(request.getContent())
                .author(user).build();
        quoteRepo.save(quote);
        return quoteToQuoteResponse(quote);
    }

    public QuoteResponse getRandomQuote() {
        List<Long> quoteIds = quoteRepo.getAllIds();
        if (quoteIds.isEmpty())
            throw new IllegalStateException(QUOTES_NOT_FOUND.getMessage());

        Random random = new Random();
        int randomIndex = random.nextInt(quoteIds.size());
        long randomId = quoteIds.get(randomIndex);
        Quote randomQuote = quoteRepo.findById(randomId)
                .orElseThrow(() -> new NullPointerException(QUOTE_NOT_FOUND.getMessage()));

        return quoteToQuoteResponse(randomQuote);
    }

    public List<QuoteResponse> getLastQuotes(int count) {
        if (count > quoteRepo.count())
            throw new IllegalArgumentException(NOT_ENOUGH_QUOTES.getMessage());

        List<Quote> quotes = quoteRepo.findLastQuotes(count);

        return quotes.stream()
                .map(QuoteMapper::quoteToQuoteResponse)
                .collect(Collectors.toList());
    }

    public List<QuoteResponse> getTopTenQuotes() {
        return getTenSorted(false);
    }

    public List<QuoteResponse> getFlopTenQuotes() {
        return getTenSorted(true);
    }

    private List<QuoteResponse> getTenSorted(boolean sort) {
        if (quoteRepo.count() < 10)
            throw new IllegalArgumentException(NOT_ENOUGH_QUOTES.getMessage());

        List<Quote> quotes = quoteRepo.findTenQuotesSorted(sort);

        return quotes.stream()
                .map(QuoteMapper::quoteToQuoteResponse)
                .collect(Collectors.toList());
    }


    public QuoteResponse updateQuote(Long quoteId, QuoteRequest request) {
        Quote quote = quoteRepo.findById(quoteId)
                .orElseThrow(() -> new NullPointerException(QUOTE_NOT_FOUND.getMessage()));

        if (request.getUserId() != null) {
            User user = userRepo.findById(request.getUserId())
                    .orElseThrow(() -> new NullPointerException(USER_NOT_FOUND.getMessage()));
            quote.setAuthor(user);
        }

        if (request.getContent() != null && !request.getContent().isEmpty()) {
            quote.setContent(request.getContent());
        }

        quoteRepo.save(quote);
        return quoteToQuoteResponse(quote);
    }

    @Transactional
    public void deleteQuote(Long quoteId) {
        Quote quote = quoteRepo.findById(quoteId)
                .orElseThrow(() -> new NullPointerException(QUOTE_NOT_FOUND.getMessage()));

        List<Vote> votes = voteRepo.findAllByQuoteId(quoteId);
        if (!votes.isEmpty())
            voteRepo.deleteAll(votes);

        quoteRepo.delete(quote);
    }
}
