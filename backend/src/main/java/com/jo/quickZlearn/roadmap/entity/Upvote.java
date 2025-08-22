package com.jo.quickZlearn.roadmap.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name="upvote", uniqueConstraints = @UniqueConstraint(columnNames = {"userId, roadmapId"}))
public class Upvote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;

    private long roadmapId;

    private LocalDateTime timeStamp= LocalDateTime.now();

}
