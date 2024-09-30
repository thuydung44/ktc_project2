package com.example.ktech_project2.repo;

import com.example.ktech_project2.model.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag , Long> {
    Optional<HashTag> findByTag(String tag);
}
