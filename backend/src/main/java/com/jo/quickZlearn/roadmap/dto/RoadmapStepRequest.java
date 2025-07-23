package com.jo.quickZlearn.roadmap.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoadmapStepRequest {
    private int stepNo;
    private String title;
    private String description;
    private String content;

}
