package com.otmane.inventory.dto;

public record AlertResponse(
        Long articleId,
        String name,
        String reference,
        String category,
        int quantity,
        int alertThreshold,
        int missingUnits,
        String level
) {
}
