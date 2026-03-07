package com.benson.user_service.models.dto.request;

/**
 * This record is used for logging in, creating a new user, or updating passwords. It is only recieved in requests and not sent back to the user.
 */
public record UserLoginDTO(
        String username,
        String password
) {}
