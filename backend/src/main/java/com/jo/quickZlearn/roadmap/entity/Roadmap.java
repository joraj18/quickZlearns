package com.jo.quickZlearn.roadmap.entity;

import com.jo.quickZlearn.auth.entity.Users;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="roadmap")
public class Roadmap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String description;

    private long upvote;

    @ManyToOne
    @JoinColumn(name="user_id") //PF to user table
    private Users createdBy;

    @OneToMany(mappedBy="roadmap", cascade= CascadeType.ALL, orphanRemoval=true)
    private List<RoadmapStep> steps;


}
