package com.otmane.inventory.repository;

import com.otmane.inventory.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    List<StockMovement> findByArticleIdOrderByMovementDateDesc(Long articleId);
}