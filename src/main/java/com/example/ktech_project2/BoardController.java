package com.example.ktech_project2;

import com.example.ktech_project2.model.Article;
import com.example.ktech_project2.model.Board;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("boards")
public class BoardController {
    private final BoardService boardservice;
    private final ArticleService articleService;

    public BoardController(BoardService service, ArticleService articleService) {
        this.boardservice = service;
        this.articleService = articleService;
    }

    // ReadAll
    @GetMapping
    public String readAll(Model model) {
        List<Article> articles = articleService.readAll();
        Collections.reverse(articles);

        model.addAttribute("boards", boardservice.readAll());
        model.addAttribute("selected",null);
        model.addAttribute("articles", articles);

        return "boards/boards.html";
    }

    @GetMapping("{boardId}")
    public String readOne(
            @PathVariable("boardId")
            Long boardId,
            Model model
    ) {
        List<Article> articles = boardservice.readOne(boardId).getArticles();
        model.addAttribute("board", boardservice.readOne(boardId));
        model.addAttribute("articles", boardservice.readOne(boardId).getArticles());
        Collections.reverse(articles);
        return "boards/read.html";
    }

}
