package com.jo.quickZlearn.roadmap.controller;

import com.jo.quickZlearn.roadmap.service.UpvoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/upvote")
public class UpvoteController {

    @Autowired
    UpvoteService upvoteService;

    @PostMapping("/{postId}")
    public ResponseEntity<String> upvote(@PathVariable long postId, @AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.ok(upvoteService.upvote(postId, userDetails.getUsername()));
    }
}
