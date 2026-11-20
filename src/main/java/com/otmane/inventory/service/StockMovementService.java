package com.otmane.inventory.service;

import com.otmane.inventory.dto.MovementRequest;
import com.otmane.inventory.model.Article;
import com.otmane.inventory.model.MovementType;
import com.otmane.inventory.model.StockMovement;
import com.otmane.inventory.repository.ArticleRepository;
import com.otmane.inventory.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockMovementService {

    private final StockMovementRepository movementRepository;
    private final ArticleRepository articleRepository;

    public List<StockMovement> findAll() {
        return movementRepository.findAll();
    }

    public List<StockMovement> findByArticle(Long articleId) {
        return movementRepository.findByArticleIdOrderByMovementDateDesc(articleId);
    }

    @Transactional
    public StockMovement register(MovementRequest request) {
        if (request.articleId() == null || request.type() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "articleId and type are required");
        }

        Article article = articleRepository.findById(request.articleId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Article not found"));

        int quantity = request.quantity();
        MovementType type = request.type();

        if (type != MovementType.ADJUSTMENT && quantity <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Quantity must be greater than 0");
        }
        if (type == MovementType.ADJUSTMENT && quantity == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Adjustment quantity cannot be 0");
        }

        int newQuantity = switch (type) {
            case ENTRY -> article.getQuantity() + quantity;
            case EXIT -> article.getQuantity() - quantity;
            case ADJUSTMENT -> article.getQuantity() + quantity;
        };

        if (newQuantity < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Not enough stock. Current quantity: " + article.getQuantity());
        }

        article.setQuantity(newQuantity);
        articleRepository.save(article);

        if (newQuantity <= article.getAlertThreshold()) {
            log.warn("RESTOCK ALERT: article '{}' (ref {}) has {} units left, threshold is {}",
                    article.getName(), article.getReference(),
                    newQuantity, article.getAlertThreshold());
        }

        StockMovement movement = new StockMovement(
                null, article, type, quantity, LocalDateTime.now(), request.note());
        return movementRepository.save(movement);
    }
}