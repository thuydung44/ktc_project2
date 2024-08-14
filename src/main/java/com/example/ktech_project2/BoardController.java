package com.example.ktech_project2;

import com.example.ktech_project2.model.Board;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


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

        model.addAttribute("boards", boardservice.readAll());
        model.addAttribute("articles", articleService.readAll());
        return "boards/boards.html";
    }

    @GetMapping("{boardId}")
    public String readOne(
            @PathVariable("boardId")
            Long boardId,

            Model model,
            Model model1
    ) {
        model.addAttribute("board", boardservice.readOne(boardId));
        model1.addAttribute("articles", boardservice.readOne(boardId).getArticles());
        return "boards/read.html";
    }

}
