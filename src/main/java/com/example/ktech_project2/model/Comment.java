package com.example.ktech_project2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Setter
    private String content;
    @Setter
    private String password;

    @Setter
    @ManyToOne
    private Article article;


    public Comment() {};

    public Comment(String content, String password, Article article) {
        this.content = content;
        this.password = password;
        this.article = article;
    }
}
