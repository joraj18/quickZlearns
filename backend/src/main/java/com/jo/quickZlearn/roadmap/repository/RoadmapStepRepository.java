package com.jo.quickZlearn.roadmap.repository;

import com.jo.quickZlearn.roadmap.entity.Roadmap;
import com.jo.quickZlearn.roadmap.entity.RoadmapStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoadmapStepRepository extends JpaRepository<RoadmapStep, Integer> {
    List<RoadmapStep> findByRoadmap(Roadmap currentRoadmap);
}
