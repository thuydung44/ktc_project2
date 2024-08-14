package com.example.ktech_project2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Setter
    String name;


    @Setter
    @OneToMany(mappedBy = "board")
    private List<Article> articles;


    public Board() {}

    public Board(String name) {
        this.name = name;
    }

}
