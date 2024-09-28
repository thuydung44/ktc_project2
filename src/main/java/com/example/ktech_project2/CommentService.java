package com.example.ktech_project2;

import com.example.ktech_project2.model.Article;
import com.example.ktech_project2.model.Comment;
import com.example.ktech_project2.repo.ArticleRepository;
import com.example.ktech_project2.repo.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public CommentService(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;

    }

    // Create
    public Comment create(
            Long articleId,
            String content,
            String password
    ) {
        Article article = articleRepository.findById(articleId).orElseThrow();

        Comment comment = new Comment(
                content,
                password,
                article
        );
        return commentRepository.save(comment);


    }
    // ReadOne
    public Comment readOne(
            Long commentId
    ) {
        return commentRepository.findById(commentId).orElseThrow();
    }


    // delete
    public void delete(Long commentId, String password){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow();
        if (comment.getPassword().equals(password)) {
            commentRepository.delete(comment);
        }
        // TODO else에서 throw
    }
}
