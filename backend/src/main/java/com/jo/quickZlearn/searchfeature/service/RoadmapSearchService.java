package com.jo.quickZlearn.searchfeature.service;

import com.jo.quickZlearn.roadmap.entity.Roadmap;
import com.jo.quickZlearn.roadmap.repository.RoadmapRepository;
import com.jo.quickZlearn.searchfeature.entity.RoadmapDocument;
import com.jo.quickZlearn.searchfeature.repository.RoadmapSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoadmapSearchService {

    @Autowired
    private RoadmapSearchRepository roadmapSearchRepository;

    @Autowired
    private RoadmapRepository roadmapRepository;

    public void sync(){
        List<Roadmap> allRoadmaps= roadmapRepository.findAll();
        List<RoadmapDocument> allDocs= allRoadmaps.stream()
                .map(r-> {
                    RoadmapDocument doc= new RoadmapDocument();
                    doc.setId(r.getId()+"");
                    doc.setTitle(r.getTitle());
                    doc.setDescription(r.getDescription());
                    return doc;
                }).toList();
        roadmapSearchRepository.saveAll(allDocs);
    }

    public List<RoadmapDocument> search(String keyword) {
        return roadmapSearchRepository.findByTitleContainingOrDescriptionContaining(keyword, keyword);
    }

    public RoadmapDocument indexRoadmap(RoadmapDocument roadmapDocument) {
        return roadmapSearchRepository.save(roadmapDocument);
    }
}
