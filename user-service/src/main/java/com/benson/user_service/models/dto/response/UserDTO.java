package com.benson.user_service.models.dto.response;

public record UserDTO(
        Long id,
        String username,
        String email
) {}
