package com.jo.quickZlearn.roadmap.repository;

import com.jo.quickZlearn.roadmap.entity.Upvote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UpvoteRepository extends JpaRepository<Upvote, Long> {
    Optional<Upvote> findByUserIdAndRoadmapId(long userId, long roadmapId);
//    long countByPostId(Long postId);
}
