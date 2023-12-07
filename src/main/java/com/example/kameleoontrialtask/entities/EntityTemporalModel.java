package com.example.kameleoontrialtask.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntityTemporalModel {

    @Column(name = "created", nullable = false, updatable = false)
    @CreatedDate
    @JsonIgnore
    private Instant created;

    @Column(name = "updated", nullable = false)
    @LastModifiedDate
    @JsonIgnore
    private Instant updated;

    @PreUpdate
    private void preUpdate() {
        updated = Instant.now();
    }

    @PrePersist
    private void prePersist() {
        created = Instant.now();
        updated = Instant.now();
    }


    // additional methods for obtaining LocalDateTime from Instant taking into account the configured time zone:

    public LocalDateTime getCreatedLocalDateTime() {
        return created.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public LocalDateTime getUpdatedLocalDateTime() {
        return updated.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
