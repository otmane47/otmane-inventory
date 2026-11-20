package com.otmane.inventory.service;

import com.otmane.inventory.dto.AlertResponse;
import com.otmane.inventory.model.Article;
import com.otmane.inventory.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertService {

    private final ArticleRepository articleRepository;

    public List<AlertResponse> getLowStockAlerts() {
        return articleRepository.findLowStock().stream()
                .map(this::toAlert)
                .toList();
    }

    private AlertResponse toAlert(Article article) {
        int quantity = article.getQuantity();
        int threshold = article.getAlertThreshold();

        String level;
        if (quantity <= 0) {
            level = "OUT_OF_STOCK";
        } else if (quantity * 2 <= threshold) {
            level = "CRITICAL";
        } else {
            level = "LOW";
        }

        String categoryName = article.getCategory() != null
                ? article.getCategory().getName()
                : null;

        return new AlertResponse(
                article.getId(),
                article.getName(),
                article.getReference(),
                categoryName,
                quantity,
                threshold,
                Math.max(threshold - quantity, 0),
                level
        );
    }
}