package com.otmane.inventory.controller;

import com.otmane.inventory.dto.MovementRequest;
import com.otmane.inventory.model.StockMovement;
import com.otmane.inventory.service.StockMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService movementService;

    @GetMapping
    public List<StockMovement> getAll() {
        return movementService.findAll();
    }

    @GetMapping("/article/{articleId}")
    public List<StockMovement> getByArticle(@PathVariable Long articleId) {
        return movementService.findByArticle(articleId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockMovement register(@RequestBody MovementRequest request) {
        return movementService.register(request);
    }
}