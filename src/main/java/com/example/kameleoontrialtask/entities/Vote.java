package com.example.kameleoontrialtask.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "votes")
@NoArgsConstructor
@Getter
@Setter
public class Vote {

    @Id
    @SequenceGenerator(name = "vote_id_seq", sequenceName = "vote_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vote_id_seq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "quote_id", nullable = false)
    private Quote quote;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "INT CHECK (rating IN (1, -1))")
    private int rating;

}
