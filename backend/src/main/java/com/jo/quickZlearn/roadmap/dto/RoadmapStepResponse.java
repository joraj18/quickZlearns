package com.jo.quickZlearn.roadmap.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RoadmapStepResponse {
    private int stepNo;
    private String title;
    private String description;
    private String content;
}