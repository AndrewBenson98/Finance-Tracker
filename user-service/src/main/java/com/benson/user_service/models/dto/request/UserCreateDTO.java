package com.benson.user_service.models.dto.request;

public record UserCreateDTO(
        String username,
        String password,
        String email
) {}
