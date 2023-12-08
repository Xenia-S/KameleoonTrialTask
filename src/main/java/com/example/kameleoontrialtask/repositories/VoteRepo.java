package com.example.kameleoontrialtask.repositories;

import com.example.kameleoontrialtask.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepo extends JpaRepository<Vote, Long> {

    List<Vote> findAllByQuoteId(Long quoteId);

    Vote findByUserIdAndQuoteId(Long userId, Long quoteId);
}
