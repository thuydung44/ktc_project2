package com.example.ktech_project2.repo;

import com.example.ktech_project2.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
