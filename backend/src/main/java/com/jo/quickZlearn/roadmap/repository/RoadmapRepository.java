package com.jo.quickZlearn.roadmap.repository;

import com.jo.quickZlearn.Auth.entity.Users;
import com.jo.quickZlearn.roadmap.entity.Roadmap;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RoadmapRepository extends JpaRepository<Roadmap, Integer> {
    List<Roadmap> findAllByOrderByIdDesc();
    List<Roadmap> findByCreatedBy(Users user);
    Optional<Roadmap> findById(long id);
    boolean existsById(long id);
    void deleteById(long id);

}
