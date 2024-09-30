package com.example.ktech_project2;

import com.example.ktech_project2.model.Article;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final BoardService boardService;

    public ArticleController(ArticleService articleService, BoardService boardService) {
        this.articleService = articleService;
        this.boardService = boardService;
    }

    //Create
    @GetMapping("create")
    public String createView(Model model) {
        model.addAttribute("boards", boardService.readAll());
        return "articles/create.html";
    }

    @PostMapping("create")
    public String create(
            @RequestParam("boardId")
            Long boardId,
            @RequestParam("title")
            String title,
            @RequestParam("content")
            String content,
            @RequestParam("password")
            String password
    ) {
        Long articleId = articleService.create(title, content, password, boardId).getId();
        return String.format("redirect:/articles/%d", articleId);
    }


    // ReadOne
    @GetMapping("{articleId}")
    public String readOne(
            @PathVariable("articleId")
            Long articleId,
            @RequestParam(value = "board", defaultValue = "0")
            Long boardId,
            Model model
    ) {
        model.addAttribute("article", articleService.readOne(articleId));
        model.addAttribute("board", boardId);
        model.addAttribute("before", articleService.getFront(boardId, articleId));
        model.addAttribute("after", articleService.getBack(boardId, articleId));

        return "articles/read.html";


    }

    @GetMapping("{articleId}/update")
    public String updateView(
            @PathVariable("articleId")
            Long articleId,
            Model model
    ) {
        model.addAttribute("boards", boardService.readAll());
        model.addAttribute("article", articleService.readOne(articleId));
        return "articles/update.html";
    }


    // Update
    @PostMapping("{articleId}/update")
    public String update(
            @PathVariable("articleId")
            Long articleId,
            @RequestParam("title")
            String title,
            @RequestParam("content")
            String content,
            @RequestParam("inputPassword")
            String inputPassword,
            Model model

    ) {
        boolean passwordIdCorrect = articleService.passwordCom(articleId, inputPassword);
        if (!passwordIdCorrect) {
            model.addAttribute("error", "password is wrong");
            return "articles/password.html";
        }
        articleService.update(articleId, title, content);
        return String.format("redirect:/articles/%d", articleId);

    }

    @GetMapping("{articleId}/delete")
    public String deleteView(
            @PathVariable("articleId")
            Long articleId,

            Model model
    ) {

        model.addAttribute("article", articleService.readOne(articleId));
        return "articles/delete.html";
    }

    // Delete
    @PostMapping("{articleId}/delete")
    public String delete(
            @PathVariable("articleId")
            Long articleId,
            Model model,
            @RequestParam("inputPassword")
            String inputPassword
    ) {
        boolean passwordIdCorrect = articleService.passwordCom(articleId, inputPassword);
        if (!passwordIdCorrect) {
            model.addAttribute("error", "password is wrong");
            return "articles/password.html";
        }
        articleService.delete(articleId);
        return "redirect:/boards";
    }

    @GetMapping("search")
    public String search(
            @RequestParam("q")
            String query,
            @RequestParam(value = "board_id", defaultValue = "0")
            Long boardId,
            @RequestParam(value = "criteria")
                    String criteria,
            Model model
    ) {
        model.addAttribute("query", query);
        model.addAttribute("boardId", boardId);
        if (!boardId.equals(0L))
            model.addAttribute("boardName", boardService.readOne(boardId).getName());
        model.addAttribute("criteria", criteria.equals("title") ? "제목": "내용");
        model.addAttribute("articles", articleService.search(boardId, criteria, query));
        return "search";
    }

    @GetMapping("hashtag")
    public String hashtag(
            @RequestParam("tag")
            String tag,
            Model model
    ) {
        model.addAttribute("tags", tag);
        model.addAttribute("articles", articleService.byTag(tag));
        return "tag";
    }

}





