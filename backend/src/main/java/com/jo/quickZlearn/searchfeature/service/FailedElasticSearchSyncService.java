package com.jo.quickZlearn.searchfeature.service;

import com.jo.quickZlearn.roadmap.entity.Roadmap;
import com.jo.quickZlearn.roadmap.exceptions.RoadmapNotFoundException;
import com.jo.quickZlearn.roadmap.repository.RoadmapRepository;
import com.jo.quickZlearn.searchfeature.entity.FailedElasticSearchSync;
import com.jo.quickZlearn.searchfeature.entity.OperationStatus;
import com.jo.quickZlearn.searchfeature.entity.RoadmapDocument;
import com.jo.quickZlearn.searchfeature.entity.SyncStatus;
import com.jo.quickZlearn.searchfeature.repository.FailedElasticSearchSyncRepository;
import com.jo.quickZlearn.searchfeature.repository.RoadmapSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FailedElasticSearchSyncService {

    @Autowired
    private FailedElasticSearchSyncRepository failedElasticSearchSyncRepository;

    @Autowired
    private RoadmapRepository roadmapRepository;

    @Autowired
    private RoadmapSearchRepository roadmapSearchRepository;

    @Scheduled(fixedRate=300000) //5mins
    public void returnSync(){
        List<FailedElasticSearchSync> failedSyncRoadmaps= failedElasticSearchSyncRepository.findByStatus(SyncStatus.FAILED);

        for(FailedElasticSearchSync failedSyncRoadmap: failedSyncRoadmaps){
            Optional<Roadmap> optionalRoadmap= roadmapRepository.findById(failedSyncRoadmap.getRoadmapId());
            if(optionalRoadmap.isEmpty()){ // by some chance the roadmap is not there in db
                continue;
            }
            Roadmap roadmap= optionalRoadmap.get();
            if(failedSyncRoadmap.getOperationType()== OperationStatus.ADD){ // if POST REQUEST
                try{
                    RoadmapDocument doc= new RoadmapDocument(
                            roadmap.getId()+"",
                            roadmap.getTitle(),
                            roadmap.getDescription()
                    );
                    roadmapSearchRepository.save(doc);
                    failedSyncRoadmap.setStatus(SyncStatus.SUCCESS);
                }catch(Exception e){ // if sync failed again don't do anything
                    System.err.println(e.getMessage());
                }
                failedSyncRoadmap.setLastAttemptTime(LocalDateTime.now());
                failedElasticSearchSyncRepository.save(failedSyncRoadmap);
            }
        }
    }
}
