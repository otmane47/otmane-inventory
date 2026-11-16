package com.otmane.inventory.service;

import com.otmane.inventory.model.Article;
import com.otmane.inventory.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article findById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Article not found"));
    }

    public Article create(Article article) {
        return articleRepository.save(article);
    }

    public Article update(Long id, Article data) {
        Article existing = findById(id);
        existing.setName(data.getName());
        existing.setReference(data.getReference());
        existing.setPrice(data.getPrice());
        existing.setQuantity(data.getQuantity());
        existing.setAlertThreshold(data.getAlertThreshold());
        existing.setCategory(data.getCategory());
        return articleRepository.save(existing);
    }
    public void delete(Long id) {
        findById(id);
        articleRepository.deleteById(id);
    }
}
