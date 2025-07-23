package com.jo.quickZlearn.roadmap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoadmapResponse {
    private Long id;
    private String title;
    private String description;
    private int upvoteCount;
    private String createdByUsername; // Simplified user info
}
