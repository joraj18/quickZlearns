package com.jo.quickZlearn.searchfeature.repository;

import com.jo.quickZlearn.searchfeature.entity.RoadmapDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface RoadmapSearchRepository  extends ElasticsearchRepository<RoadmapDocument, String> {
    List<RoadmapDocument> findByTitleContainingOrDescriptionContaining(String title, String description);
}
