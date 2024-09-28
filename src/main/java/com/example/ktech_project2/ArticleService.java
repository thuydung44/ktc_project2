package com.example.ktech_project2;

import com.example.ktech_project2.model.Article;
import com.example.ktech_project2.model.Board;
import com.example.ktech_project2.repo.ArticleRepository;
import com.example.ktech_project2.repo.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final BoardRepository boardRepository;
    private final ArticleRepository articleRepository;
    public ArticleService(ArticleRepository articleRepository, BoardRepository boardRepository) {
        this.articleRepository = articleRepository;
        this.boardRepository = boardRepository;
    }

    // Create
    public Article create(
            String title,
            String content,
            String password,
            Long boardId
    ){
        Board board = boardRepository.findById(boardId).orElseThrow();

        Article article = new Article(title, content, password, board);
        return articleRepository.save(article);

    }
    // ReadAll
    public List<Article> readAll() {
        return articleRepository.findAll();
    }

    // ReadOne
    public Article readOne(
            Long articleId
    ) {
        return articleRepository.findById(articleId).orElseThrow();

    }

    // Update
    public Article update(
            Long articleId,
            String title,
            String content
    ) {
        Optional<Article> optionalArticle = articleRepository.findById(articleId);
        if (optionalArticle.isEmpty()) {
            return null;
        }

        Article target = optionalArticle.get();
        target.setTitle(title);
        target.setContent(content);
        return articleRepository.save(target);


    }

    public boolean passwordCom(
            Long id,
            String inputPass
    ) {
        Article article = articleRepository.findById(id).orElseThrow();
        if (article == null || !article.getPassword().equals(inputPass)) {
            return false;
        }
        return true;
    }

    // Delete
    public void delete(Long articleId) {

        articleRepository.deleteById(articleId);
    }

    public Long getFront(Long boardId, Long articleId) {
        Optional<Article> target;
        if (boardId == 0L) {
            target = articleRepository.findFirstByIdAfter(articleId);
        } else {
            target = articleRepository.findFirstByBoardIdAndIdAfter(boardId, articleId);
        }
        return target.map(Article::getId).orElse(null);
    }

    public Long getBack(Long boardId, Long articleId) {
        Optional<Article> target;
        if (boardId == 0) {
            target = articleRepository.findFirstByIdBeforeOrderByIdDesc(articleId);
        } else {
            target = articleRepository.findFirstByBoardIdAndIdBeforeOrderByIdDesc(boardId, articleId);
        }
        return target.map(Article::getId).orElse(null);
    }
















}

