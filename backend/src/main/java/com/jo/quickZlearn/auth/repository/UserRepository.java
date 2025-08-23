package com.jo.quickZlearn.auth.repository;

import com.jo.quickZlearn.auth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
//    Users findByEmail(String email);
}
