package com.jo.quickZlearn.roadmap.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="roadmap_step")
public class RoadmapStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int stepNo;

    private String title;

    private String description;

    private String content;

    @ManyToOne
    @JoinColumn(name="roadmap_id")
    private Roadmap roadmap;


}
