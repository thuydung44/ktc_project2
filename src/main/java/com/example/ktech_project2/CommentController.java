package com.example.ktech_project2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/articles/{articleId}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Create
    @PostMapping
    public String create(
            @PathVariable("articleId")
            Long articleId,
            @RequestParam("content")
            String content,
            @RequestParam("password")
            String password
    ) {
        commentService.create(articleId, content, password);
        return String.format("redirect:/articles/%d", articleId);
    }


    // Delete
    @PostMapping("{commentId}/delete")
    public String delete(
            @PathVariable("articleId")
            Long articleId,
            @PathVariable("commentId")
            Long commentId,
            @RequestParam("password")
            String password,
            Model model

    ) {
        commentService.delete(commentId, password);
        return String.format("redirect:/articles/%d", articleId);
    }

}
