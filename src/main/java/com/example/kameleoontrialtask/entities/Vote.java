package com.example.kameleoontrialtask.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "votes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"quote_id", "user_id"}))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Vote extends EntityTemporalModel{

    @Id
    @SequenceGenerator(name = "vote_id_seq", sequenceName = "vote_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vote_id_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_id", nullable = false)
    private Quote quote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "INT CHECK (rating IN (1, -1))")
    private int rating;

}
