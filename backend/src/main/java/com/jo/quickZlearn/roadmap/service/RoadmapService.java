package com.jo.quickZlearn.roadmap.service;

import com.jo.quickZlearn.auth.entity.Users;
import com.jo.quickZlearn.auth.repository.UserRepository;
import com.jo.quickZlearn.roadmap.dto.RoadmapRequest;
import com.jo.quickZlearn.roadmap.dto.RoadmapResponse;
import com.jo.quickZlearn.roadmap.dto.RoadmapStepResponse;
import com.jo.quickZlearn.roadmap.entity.Roadmap;
import com.jo.quickZlearn.roadmap.entity.RoadmapStep;
import com.jo.quickZlearn.roadmap.exceptions.RoadmapNotFoundException;
import com.jo.quickZlearn.roadmap.repository.RoadmapRepository;
import com.jo.quickZlearn.roadmap.repository.RoadmapStepRepository;
import com.jo.quickZlearn.searchfeature.entity.FailedElasticSearchSync;
import com.jo.quickZlearn.searchfeature.entity.OperationStatus;
import com.jo.quickZlearn.searchfeature.entity.RoadmapDocument;
import com.jo.quickZlearn.searchfeature.entity.SyncStatus;
import com.jo.quickZlearn.searchfeature.repository.FailedElasticSearchSyncRepository;
import com.jo.quickZlearn.searchfeature.repository.RoadmapSearchRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoadmapService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoadmapRepository roadmapRepository;

    @Autowired
    private RoadmapStepRepository roadmapStepRepository;

    @Autowired
    private RoadmapSearchRepository roadmapSearchRepository;

    @Autowired
    private FailedElasticSearchSyncRepository failedElasticSearchSyncRepository;

    //create roadmap
    public Roadmap createRoadmap(RoadmapRequest request, String email) {
        Users user= userRepo.findByEmail(email).orElseThrow();

        Roadmap roadmap= new Roadmap();
        roadmap.setCreatedBy(user);
        roadmap.setTitle(request.getTitle());
        roadmap.setDescription(request.getDescription());
        roadmap.setUpvote(0);

        List<RoadmapStep> steps = request.getSteps().stream().map(stepReq -> {
            RoadmapStep step = new RoadmapStep();
            step.setTitle(stepReq.getTitle());
            step.setDescription(stepReq.getDescription());
            step.setStepNo(stepReq.getStepNo()); // updated to stepNo
            step.setContent(stepReq.getContent()); // updated to content
            step.setRoadmap(roadmap);  // important for reverse mapping
            return step;
        }).collect(Collectors.toList());
        roadmap.setSteps(steps);

        Roadmap savedRoadmap= roadmapRepository.save(roadmap);

        //create a doc in RoadmapDocument (for ES)
        try{
            RoadmapDocument doc= new RoadmapDocument(
                    savedRoadmap.getId()+"",
                    savedRoadmap.getTitle(),
                    savedRoadmap.getDescription()
            );
            roadmapSearchRepository.save(doc);
        }
        catch(Exception e){
            FailedElasticSearchSync failedSynRoadmap= new FailedElasticSearchSync(
                    null,
                    savedRoadmap.getId(),
                    OperationStatus.ADD,
                    LocalDateTime.now(),
                    SyncStatus.FAILED
            );
            failedElasticSearchSyncRepository.save(failedSynRoadmap);
            System.out.println("Doc failed to sync:" + e.getMessage());
        }
        return savedRoadmap;
    }


    //return all roadmap
    public List<RoadmapResponse>viewAllRoadmap(){
        List<Roadmap> allRoadmaps = roadmapRepository.findAll();

        return allRoadmaps.stream().map(roadmap -> new RoadmapResponse(
                roadmap.getId(),
                roadmap.getTitle(),
                roadmap.getDescription(),
                (int) roadmap.getUpvote(),
                roadmap.getCreatedBy().getUsername()
        )).collect(Collectors.toList());
    }


    //return users published roadmaps
    public List<RoadmapResponse>viewMyRoadmaps(String email){
        Optional<Users> user= userRepo.findByEmail(email);
        if(!user.isPresent()){
            return new ArrayList<>();
        }
        Users actualUser= user.get();
        List<Roadmap> myRoadmaps= roadmapRepository.findByCreatedBy(actualUser);
        return myRoadmaps.stream()
                .map(roadmap -> new RoadmapResponse(
                        roadmap.getId(),
                        roadmap.getTitle(),
                        roadmap.getDescription(),
                        (int) roadmap.getUpvote(),
                        roadmap.getCreatedBy().getUsername()
                ))
                .collect(Collectors.toList());
    }

    //return roadmap content
    public List<RoadmapStepResponse>getRoadmapContent(long roadmapId){
        System.out.println("Entered getRoadmapContent");
        Optional<Roadmap> optionalRoadmap= roadmapRepository.findById(roadmapId);
        Roadmap roadmap= optionalRoadmap.orElseThrow(() -> new RoadmapNotFoundException("No such roadmap found"));

        List<RoadmapStep> stepList = roadmapStepRepository.findByRoadmap(roadmap);
        return stepList.stream()
                .map(step -> new RoadmapStepResponse(
                        step.getStepNo(),
                        step.getTitle(),
                        step.getDescription(),
                        step.getContent()
                ))
                .collect(Collectors.toList());
    }

    //delete roadmap
    @Transactional
    public void deleteRoadmap(Long id, String email){
        Optional<Roadmap> optionalRoadmap= roadmapRepository.findById(id);
        Roadmap roadmap= optionalRoadmap.orElseThrow(() -> new RoadmapNotFoundException("Not found"));

        System.out.println("email inside table: "+roadmap.getCreatedBy().getEmail());
        System.out.println("email in argument: "+email);
        if(!roadmap.getCreatedBy().getEmail().equals(email)){
            System.out.println("Not authorized");
            throw new AccessDeniedException("You are not authorized to delete this roadmap.");
        }

        try {
            System.out.println("inside try block");
            roadmapRepository.delete(roadmap);
            System.out.println("deleted successfully");
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to delete roadmap. Reason: " + e.getMessage());
        }
    }


}
