package com.example.ktech_project2.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class ArticleHashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Article article;

    @ManyToOne
    private HashTag hashTag;

    public ArticleHashTag(Article article, HashTag hashTag) {
        this.article = article;
        this.hashTag = hashTag;
    }
}
