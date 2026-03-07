package com.benson.user_service.utils;

import com.benson.user_service.models.User;
import com.benson.user_service.models.dto.request.UserCreateDTO;
import com.benson.user_service.models.dto.response.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User user);
    User toEntity(UserDTO dto);
    User toEntity(UserCreateDTO userCreateDTO);
}
