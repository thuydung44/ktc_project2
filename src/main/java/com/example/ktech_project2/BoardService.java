package com.example.ktech_project2;

import com.example.ktech_project2.model.Board;
import com.example.ktech_project2.repo.BoardRepository;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class BoardService {
    private final BoardRepository repository;

    public BoardService(BoardRepository repository) {
        this.repository = repository;
    }

    // ReadAll
    public List<Board> readAll() {
        return repository.findAll();
    }
    //ReadOne
    public Board readOne(Long boardId){
        return repository.findById(boardId).orElseThrow();
    }
}
