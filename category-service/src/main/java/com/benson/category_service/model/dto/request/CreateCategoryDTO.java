package com.benson.category_service.model.dto.request;

import java.math.BigDecimal;

public record CreateCategoryDTO(
        String name,
        Long userId,
        BigDecimal budget
) {
}
