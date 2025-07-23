package com.jo.quickZlearn.roadmap.service;

import com.jo.quickZlearn.Auth.entity.Users;
import com.jo.quickZlearn.Auth.repository.UserRepository;
import com.jo.quickZlearn.roadmap.dto.RoadmapRequest;
import com.jo.quickZlearn.roadmap.dto.RoadmapResponse;
import com.jo.quickZlearn.roadmap.dto.RoadmapStepRequest;
import com.jo.quickZlearn.roadmap.dto.RoadmapStepResponse;
import com.jo.quickZlearn.roadmap.entity.Roadmap;
import com.jo.quickZlearn.roadmap.entity.RoadmapStep;
import com.jo.quickZlearn.roadmap.repository.RoadmapRepository;
import com.jo.quickZlearn.roadmap.repository.RoadmapStepRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoadmapService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoadmapRepository roadmapRepo;

    @Autowired
    private RoadmapStepRepository roadmapStepRepo;

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
        return roadmapRepo.save(roadmap);
    }


    //return all roadmap
    public List<RoadmapResponse>viewAllRoadmap(){
        List<Roadmap> allRoadmaps = roadmapRepo.findAll();

        return allRoadmaps.stream().map(roadmap -> new RoadmapResponse(
                roadmap.getId(),
                roadmap.getTitle(),
                roadmap.getDescription(),
                (int) roadmap.getUpvote(), // cast long to int if needed
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
        List<Roadmap> myRoadmaps= roadmapRepo.findByCreatedBy(actualUser);
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
        Optional<Roadmap> optionalRoadmap= roadmapRepo.findById(roadmapId);
        Roadmap roadmap= optionalRoadmap.orElseThrow(() -> new NoSuchElementException("Not found"));

        List<RoadmapStep> stepList = roadmapStepRepo.findByRoadmap(roadmap);
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
        Optional<Roadmap> optionalRoadmap= roadmapRepo.findById(id);
        Roadmap roadmap= optionalRoadmap.orElseThrow(() -> new NoSuchElementException("Not found"));

        System.out.println("email inside table: "+roadmap.getCreatedBy().getEmail());
        System.out.println("email in argument: "+email);
        if(!roadmap.getCreatedBy().getEmail().equals(email)){
            System.out.println("Not authorized");
            throw new AccessDeniedException("You are not authorized to delete this roadmap.");
        }

        try {
            System.out.println("inside try block");
            roadmapRepo.delete(roadmap);
            System.out.println("deleted successfully");
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to delete roadmap. Reason: " + e.getMessage());
        }
    }


}
