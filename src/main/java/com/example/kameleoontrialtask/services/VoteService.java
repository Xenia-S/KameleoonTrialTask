package com.example.kameleoontrialtask.services;

import com.example.kameleoontrialtask.dto.VoteRequest;
import com.example.kameleoontrialtask.entities.EntityTemporalModel;
import com.example.kameleoontrialtask.entities.Quote;
import com.example.kameleoontrialtask.entities.User;
import com.example.kameleoontrialtask.entities.Vote;
import com.example.kameleoontrialtask.repositories.QuoteRepo;
import com.example.kameleoontrialtask.repositories.UserRepo;
import com.example.kameleoontrialtask.repositories.VoteRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.example.kameleoontrialtask.util.Messages.QUOTE_NOT_FOUND;
import static com.example.kameleoontrialtask.util.Messages.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepo voteRepo;
    private final QuoteRepo quoteRepo;
    private final UserRepo userRepo;

    public Map<LocalDateTime, Integer> getScoreChart(Long quoteId) {
        if (!quoteRepo.existsById(quoteId))
            throw new IllegalArgumentException(QUOTE_NOT_FOUND.getMessage());

        List<Vote> votes = voteRepo.findAllByQuoteId(quoteId);
        votes.sort(Comparator.comparing(EntityTemporalModel::getUpdatedLocalDateTime));

        Map<LocalDateTime, Integer> scoreChart = new LinkedHashMap<>();
        for (Vote vote : votes) {
            Integer score = votes.stream()
                    .filter(v -> v.getUpdatedLocalDateTime().isBefore(vote.getUpdatedLocalDateTime()))
                    .mapToInt(Vote::getRating)
                    .sum() + vote.getRating();
            scoreChart.put(vote.getUpdatedLocalDateTime(), score);
        }

       return scoreChart;

    }

    public void voteQuote(VoteRequest request) {
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new NullPointerException(USER_NOT_FOUND.getMessage()));
        Quote quote = quoteRepo.findById(request.getQuoteId())
                .orElseThrow(() -> new NullPointerException(QUOTE_NOT_FOUND.getMessage()));

        Vote vote = voteRepo.findByUserIdAndQuoteId(user.getId(), quote.getId());
        if (vote == null) {
            vote = Vote.builder()
                    .quote(quote)
                    .user(user).build();
        }
        vote.setRating(Integer.parseInt(request.getRating()));
        voteRepo.save(vote);
    }
}
