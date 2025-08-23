package com.jo.quickZlearn.searchfeature.controller;

import com.jo.quickZlearn.roadmap.entity.Roadmap;
import com.jo.quickZlearn.roadmap.service.RoadmapService;
import com.jo.quickZlearn.searchfeature.entity.RoadmapDocument;
import com.jo.quickZlearn.searchfeature.service.RoadmapSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roadmap")
public class RoadmapSearchController {

    @Autowired
    private RoadmapSearchService roadmapSearchService;

    @GetMapping("/sync")
    public String sync(){
        roadmapSearchService.sync();
        return "Data synchronized from db to elastic search";
    }

    @GetMapping("/search")
    public ResponseEntity<List<RoadmapDocument>> search(@RequestParam("q") String keyword){
        List<RoadmapDocument> searchResult= roadmapSearchService.search(keyword);
        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    @PostMapping("/index")
    public ResponseEntity<RoadmapDocument> index(@RequestBody RoadmapDocument roadmapDocument) {
        return ResponseEntity.ok(roadmapSearchService.indexRoadmap(roadmapDocument));
    }

}
