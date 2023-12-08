package com.example.kameleoontrialtask.util;

import com.example.kameleoontrialtask.entities.Quote;
import com.example.kameleoontrialtask.entities.User;
import com.example.kameleoontrialtask.entities.Vote;
import com.example.kameleoontrialtask.repositories.QuoteRepo;
import com.example.kameleoontrialtask.repositories.UserRepo;
import com.example.kameleoontrialtask.repositories.VoteRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class TestDataLoader implements ApplicationRunner {

    private final UserRepo userRepository;
    private final QuoteRepo quoteRepository;
    private final VoteRepo voteRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        List<User> users = createUsers();
        List<Quote> quotes = createQuotes(users);
        createVotes(quotes, users);
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = User.builder()
                    .name("User " + i)
                    .email("user" + i + "@test.com")
                    .password("Qwerty12345")
                    .build();
            users.add(user);
        }
        userRepository.saveAll(users);
        return users;
    }

    private List<Quote> createQuotes(List<User> users) {
        List<Quote> quotes = new ArrayList<>();
        String[] quoteContents = {
                "The only limit to our realization of tomorrow will be our doubts of today.",
                "The only way to do great work is to love what you do.",
                "In the middle of difficulty lies opportunity.",
                "The best way to predict the future is to create it.",
                "Success is not final, failure is not fatal: It is the courage to continue that counts.",
                "The only source of knowledge is experience.",
                "Your time is limited, don't waste it living someone else's life.",
                "To be yourself in a world that is constantly trying to make you something else is the greatest accomplishment.",
                "The future belongs to those who believe in the beauty of their dreams.",
                "Change your thoughts and you change your world.",
                "It always seems impossible until it's done.",
                "The only thing we have to fear is fear itself.",
                "Believe you can and you're halfway there.",
                "What lies behind us and what lies before us are tiny matters compared to what lies within us.",
                "The greatest glory in living lies not in never falling, but in rising every time we fall.",
                "You miss 100% of the shots you don't take.",
                "The only impossible journey is the one you never begin.",
                "The purpose of our lives is to be happy.",
                "Life is what happens when you're busy making other plans.",
                "Get busy living or get busy dying.",
                "You have within you right now, everything you need to deal with whatever the world can throw at you.",
                "What you get by achieving your goals is not as important as what you become by achieving your goals.",
                "I find that the harder I work, the more luck I seem to have.",
                "Do not wait to strike till the iron is hot, but make it hot by striking.",
                "The only place where success comes before work is in the dictionary.",
                "Success is not how high you have climbed, but how you make a positive difference to the world.",
                "If you want to achieve greatness stop asking for permission.",
                "The only thing standing between you and your goal is the story you keep telling yourself as to why you can't achieve it.",
                "The best revenge is massive success.",
                "You get in life what you have the courage to ask for."
        };

        for (int i = 0; i < quoteContents.length; i++) {
            Quote quote = Quote.builder()
                    .content(quoteContents[i])
                    .author(users.get(i % users.size()))
                    .build();
            quotes.add(quote);
        }
        quoteRepository.saveAll(quotes);
        return quotes;
    }

    private void createVotes(List<Quote> quotes, List<User> users) {
        List<Vote> votes = new ArrayList<>();
        Map<Long, Set<Long>> votedQuotesByUser = new HashMap<>();

        for (int i = 0; i < 200; i++) {
            Random random = new Random();
            User user = users.get(i % users.size());
            Quote quote = quotes.get(random.nextInt(quotes.size()));
            int rating = random.nextBoolean() ? 1 : -1;

            Set<Long> votedQuotes = votedQuotesByUser.computeIfAbsent(user.getId(), k -> new HashSet<>());
            while (votedQuotes.contains(quote.getId())) {
                quote = quotes.get(random.nextInt(quotes.size()));
            }

            votedQuotes.add(quote.getId());

            Vote vote = Vote.builder()
                    .quote(quote)
                    .user(user)
                    .rating(rating)
                    .build();
            votes.add(vote);
        }

        voteRepository.saveAll(votes);
    }

}
