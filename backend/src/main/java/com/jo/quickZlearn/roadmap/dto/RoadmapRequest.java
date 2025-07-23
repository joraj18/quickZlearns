package com.jo.quickZlearn.roadmap.dto;

import com.jo.quickZlearn.roadmap.entity.RoadmapStep;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoadmapRequest {
    private String title;
    private String description;
    private List<RoadmapStepRequest> steps= new ArrayList<>();

}
