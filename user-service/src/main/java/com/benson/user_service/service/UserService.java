package com.benson.user_service.service;

import com.benson.user_service.exceptions.UserAlreadyExistsException;
import com.benson.user_service.exceptions.UserNotFoundException;
import com.benson.user_service.exceptions.InvalidPasswordException;
import com.benson.user_service.exceptions.UsernameAlreadyExistsException;
import com.benson.user_service.models.dto.request.*;
import com.benson.user_service.models.dto.response.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserCreateDTO userCreateDTO) throws UserAlreadyExistsException;

    UserDTO getUserByUsername(String username) throws UserNotFoundException;

    List<UserDTO> getAllUsers();

    UserDTO updateUserPassword(String username, UpdatePasswordDTO updatePasswordDTO) throws InvalidPasswordException;

    UserDTO updateUserEmail(String username, UpdateEmailDTO updateEmailDTO) throws UserNotFoundException;

    UserDTO updateUsername(String existingUsername, UpdateUsernameDTO updateUsernameDTO) throws UserNotFoundException, UsernameAlreadyExistsException;

    void deleteUser(String username) throws UserNotFoundException;

}
