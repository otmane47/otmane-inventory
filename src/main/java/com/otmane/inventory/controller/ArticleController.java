package com.otmane.inventory.controller;

import com.otmane.inventory.model.Article;
import com.otmane.inventory.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public List<Article> getAll() {
        return articleService.findAll();
    }

    @GetMapping("/{id}")
    public Article getOne(@PathVariable Long id) {
        return articleService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Article create(@RequestBody Article article) {
        return articleService.create(article);
    }

    @PutMapping("/{id}")
    public Article update(@PathVariable Long id, @RequestBody Article article) {
        return articleService.update(id, article);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        articleService.delete(id);
    }
}