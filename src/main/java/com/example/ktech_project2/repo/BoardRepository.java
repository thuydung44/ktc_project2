package com.example.ktech_project2.repo;

import com.example.ktech_project2.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
