package com.example.kameleoontrialtask.repositories;

import com.example.kameleoontrialtask.entities.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuoteRepo extends JpaRepository<Quote, Long> {

    @Query(value = "select id from quotes", nativeQuery = true)
    List<Long> getAllIds();

    @Query(value = "SELECT q.* FROM quotes q " +
            " LEFT JOIN votes v ON v.quote_id = q.id " +
            " GROUP BY q.id " +
            " ORDER BY " +
            " CASE " +
            "   WHEN :ascSort THEN 1 " +
            "   ELSE -1 " +
            " END * SUM(v.rating) ASC, q.id" +
            " LIMIT 10", nativeQuery = true)
    List<Quote> findTenQuotesSorted(@Param("ascSort") boolean ascSort);


    @Query(value = "select * from quotes " +
            " order by created desc, id limit :count", nativeQuery = true)
    List<Quote> findLastQuotes(@Param("count") int count);

    boolean existsById(Long quoteId);
}
