package com.jo.quickZlearn.roadmap.service;

import com.jo.quickZlearn.Auth.repository.UserRepository;
import com.jo.quickZlearn.roadmap.entity.Roadmap;
import com.jo.quickZlearn.roadmap.entity.Upvote;
import com.jo.quickZlearn.roadmap.repository.RoadmapRepository;
import com.jo.quickZlearn.roadmap.repository.UpvoteRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpvoteService {

    @Autowired
    private UpvoteRepository upvoteRepository;
    @Autowired
    private RoadmapRepository roadmapRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String upvote(long roadmapId, String email){
        long userId= userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Roadmap not found with id: " + roadmapId))
                .getId();

        if(upvoteRepository.findByUserIdAndRoadmapId(userId, roadmapId).isPresent()){ //remove upvote
            //delete the upvote record
            Upvote upvote= upvoteRepository.findByUserIdAndRoadmapId(userId, roadmapId)
                    .orElseThrow(() -> new RuntimeException("Some error occurred  "));
            upvoteRepository.delete(upvote);

            //decrement roadmap upvote count
            Roadmap roadmap = roadmapRepository.findById(roadmapId)
                    .orElseThrow(() -> new RuntimeException("Roadmap not found with id: " + roadmapId));
            roadmap.setUpvote(roadmap.getUpvote()-1);
            roadmapRepository.save(roadmap);
            return "reverted Upvote!";

        }
        else{ //upvote
            
            //insert new upvote record
            Upvote upvote= new Upvote();
            upvote.setRoadmapId(roadmapId);
            upvote.setUserId(userId);
            upvoteRepository.save(upvote);

            //increment roadmap upvote count
            Roadmap roadmap = roadmapRepository.findById(roadmapId)
                    .orElseThrow(() -> new RuntimeException("Roadmap not found with id: " + roadmapId));
            roadmap.setUpvote(roadmap.getUpvote()+1);
            roadmapRepository.save(roadmap);
            return "Upvoted!";
        }
    }
}
