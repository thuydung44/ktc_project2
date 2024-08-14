package com.example.ktech_project2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("articles/{articleId}/comments")
public class CommentController {
    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Create
    @PostMapping("create")
    public String create(
            @PathVariable("articleId")
            Long articleId,
            @RequestParam("content")
            String content,
            @RequestParam("password")
            String password
    ){
        commentService.create(articleId, content, password);
        return String.format("redirect:/articles/%d", articleId);
    }
    //ReadOne
    @PostMapping("{commentId}")
    public String readOne(
            @PathVariable("commentId")
            Long commentId,
            Model model
    ) {
        model.addAttribute("comment", commentService.readOne(commentId));
        return "articles/read.html";
    }
    // Delete
    @PostMapping("{commentId}/delete")
    public String delete(
            @PathVariable("commentId")
            Long commentId
    ) {
        commentService.delete(commentId);
        return "redirect:/comments";
    }

}
