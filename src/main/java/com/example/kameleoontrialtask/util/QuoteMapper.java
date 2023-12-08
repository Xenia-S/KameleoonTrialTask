package com.example.kameleoontrialtask.util;

import com.example.kameleoontrialtask.dto.QuoteResponse;
import com.example.kameleoontrialtask.entities.Quote;
import com.example.kameleoontrialtask.entities.Vote;

public class QuoteMapper {

    public static QuoteResponse quoteToQuoteResponse(Quote quote) {
        QuoteResponse response = new QuoteResponse();
        response.setId(quote.getId());
        response.setContent(quote.getContent());
        response.setPostedBy(quote.getAuthor());
        response.setCreated(quote.getCreatedLocalDateTime());
        response.setUpdated(quote.getUpdatedLocalDateTime());
        if (quote.getVotes() != null && !quote.getVotes().isEmpty()) {
            int score = quote.getVotes().stream()
                    .mapToInt(Vote::getRating)
                    .sum();
            response.setScore(score);
        } else {
            response.setScore(0);
        }
        return response;
    }
}
