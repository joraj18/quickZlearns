package com.jo.quickZlearn.roadmap.controller;

import com.jo.quickZlearn.Auth.entity.Users;
import com.jo.quickZlearn.roadmap.dto.RoadmapRequest;
import com.jo.quickZlearn.roadmap.dto.RoadmapResponse;
import com.jo.quickZlearn.roadmap.dto.RoadmapStepResponse;
import com.jo.quickZlearn.roadmap.entity.Roadmap;
import com.jo.quickZlearn.roadmap.entity.RoadmapStep;
import com.jo.quickZlearn.roadmap.service.RoadmapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roadmap")
public class RoadmapController {

    @Autowired
    private RoadmapService roadmapService;
    
    @PostMapping("/create")
    public ResponseEntity<Roadmap> createRoadmap(@RequestBody RoadmapRequest request, @AuthenticationPrincipal UserDetails userDetails){
    Roadmap roadmap= roadmapService.createRoadmap(request, userDetails.getUsername());
    return ResponseEntity.status(HttpStatus.CREATED).body(roadmap);
    }

    @GetMapping("/view") //list of roadmap(not the steps)
    public ResponseEntity<List<RoadmapResponse>> viewRoadmap(){
        List<RoadmapResponse> allRoadmap= roadmapService.viewAllRoadmap();
        return ResponseEntity.ok(allRoadmap);
    }

    @GetMapping("/viewMyRoadmaps")
    public ResponseEntity<List<RoadmapResponse>> viewMyRoadmaps(@AuthenticationPrincipal UserDetails userDetails){
        List<RoadmapResponse> myRoadmaps= roadmapService.viewMyRoadmaps(userDetails.getUsername());
        return ResponseEntity.ok(myRoadmaps);
    }

    @GetMapping("/content/{roadmapId}")
    public ResponseEntity<List<RoadmapStepResponse>> getRoadmapContent(@PathVariable long roadmapId){
        List<RoadmapStepResponse> roadmapContent= roadmapService. getRoadmapContent(roadmapId);
        return ResponseEntity.ok(roadmapContent);
    }

    @DeleteMapping("/{roadmapId}")
    public ResponseEntity<String> deleteRoadmap(@PathVariable long roadmapId, @AuthenticationPrincipal UserDetails userDetails){
        roadmapService.deleteRoadmap(roadmapId, userDetails.getUsername());
        return ResponseEntity.ok("Roadmap deleted successfully");
    }


}
