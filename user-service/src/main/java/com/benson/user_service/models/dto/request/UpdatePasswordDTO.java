package com.benson.user_service.models.dto.request;

public record UpdatePasswordDTO(
        String existingPassword,
        String newPassword
) {}
