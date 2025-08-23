package com.jo.quickZlearn.searchfeature.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FailedElasticSearchSync {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roadmapId;

    @Enumerated(EnumType.STRING)
    private OperationStatus operationType; // ADD/DELETE

    private LocalDateTime lastAttemptTime;

    @Enumerated(EnumType.STRING)
    private SyncStatus status; // SUCCESS/FAILED
}
