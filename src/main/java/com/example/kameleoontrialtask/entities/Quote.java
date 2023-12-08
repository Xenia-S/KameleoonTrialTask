package com.example.kameleoontrialtask.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "quotes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Quote extends EntityTemporalModel {

    @Id
    @SequenceGenerator(name = "quote_id_seq", sequenceName = "quote_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quote_id_seq")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToMany(mappedBy = "quote", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Vote> votes;
}
