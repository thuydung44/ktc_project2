package com.example.ktech_project2;

import com.example.ktech_project2.model.Article;
import com.example.ktech_project2.model.ArticleHashTag;
import com.example.ktech_project2.model.Board;
import com.example.ktech_project2.model.HashTag;
import com.example.ktech_project2.repo.ArticleHashTagRepository;
import com.example.ktech_project2.repo.ArticleRepository;
import com.example.ktech_project2.repo.BoardRepository;
import com.example.ktech_project2.repo.HashTagRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleService {
    private final BoardRepository boardRepository;
    private final ArticleRepository articleRepository;
    private final ArticleHashTagRepository articleHashTagRepository;
    private final HashTagRepository hashTagRepository;

    public ArticleService(
            ArticleRepository articleRepository,
            BoardRepository boardRepository,
            ArticleHashTagRepository articleHashTagRepository,
            HashTagRepository hashTagRepository) {
        this.articleRepository = articleRepository;
        this.boardRepository = boardRepository;
        this.articleHashTagRepository = articleHashTagRepository;
        this.hashTagRepository = hashTagRepository;
    }

    // Create
    public Article create(
            String title,
            String content,
            String password,
            Long boardId
    ) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        Article article = new Article(title, content, password, board);
        articleRepository.save(article);
        for (HashTag hashTag : createHashTags(article.getContent())) {
            articleHashTagRepository.save(new ArticleHashTag(article, hashTag));
        }
        return articleRepository.save(article);
    }

    List<Article> byTag(String tag) {
        HashTag hashTag = hashTagRepository.findByTag(tag).orElseThrow();
        List<Article> articles = new ArrayList<>();
        for (ArticleHashTag articleHashTag : hashTag.getArticleHasTag()) {
            articles.add(articleHashTag.getArticle());
        }
        return articles;
    }

    private Set<HashTag> createHashTags(String content) {
        String[] words = content.split(" ");
        Set<HashTag> hashTags = new HashSet<>();
        for (String word : words) {
            if (word.startsWith("#")) {
                Optional<HashTag> hashTagOptional = hashTagRepository.findByTag(word);
                if (hashTagOptional.isPresent()) hashTags.add(hashTagOptional.get());
                else hashTags.add(hashTagRepository.save(new HashTag(word)));
            }
        }
        return hashTags;

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

    public List<Article> search(Long boardId, String criteria, String query) {
        List<Article> results = new ArrayList<>();
        List<Article> articles;
        if (boardId != 0L) {
            articles = criteria.equals("title")
                    ? articleRepository.findByTitleContainsAndBoardId(query, boardId)
                    : articleRepository.findByContentContainsAndBoardId(query, boardId);
        } else {
            articles = criteria.equals("title")
                    ? articleRepository.findByTitleContains(query)
                    : articleRepository.findByContentContains(query);
        }
        for (Article article : articles) {
            results.add(article);
        }
        return results;

    }


}

