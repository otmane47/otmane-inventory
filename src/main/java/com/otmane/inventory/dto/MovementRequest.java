package com.otmane.inventory.dto;

import com.otmane.inventory.model.MovementType;

public record MovementRequest(
        Long articleId,
        MovementType type,
        int quantity,
        String note
) {
}