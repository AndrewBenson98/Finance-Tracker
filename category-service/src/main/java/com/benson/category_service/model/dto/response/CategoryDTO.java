package com.benson.category_service.model.dto.response;

import java.math.BigDecimal;

public record CategoryDTO(
        Long id,
        String name,
        Long userId,
        BigDecimal budget
) {
}
