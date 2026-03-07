package com.benson.user_service.service;

import com.benson.user_service.exceptions.InvalidPasswordException;
import com.benson.user_service.exceptions.UserAlreadyExistsException;
import com.benson.user_service.exceptions.UserNotFoundException;
import com.benson.user_service.exceptions.UsernameAlreadyExistsException;
import com.benson.user_service.models.User;
import com.benson.user_service.models.dto.request.UpdateEmailDTO;
import com.benson.user_service.models.dto.request.UpdatePasswordDTO;
import com.benson.user_service.models.dto.request.UpdateUsernameDTO;
import com.benson.user_service.models.dto.request.UserCreateDTO;
import com.benson.user_service.models.dto.response.UserDTO;
import com.benson.user_service.repository.UserRepository;
import com.benson.user_service.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(@Autowired UserRepository userRepository, @Autowired UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Creates a new user in the system. Checks if the username is already taken before saving to the database.
     * @param userCreateDTO
     * @return
     * @throws UserAlreadyExistsException
     */
    @Override
    public UserDTO createUser(UserCreateDTO userCreateDTO) throws UserAlreadyExistsException {

        String username = userCreateDTO.username(); // Check before mapping to save resources

        userRepository.findByUsername(username).ifPresent(u -> {
            throw new UserAlreadyExistsException("Username " + username + " is taken");
        });

        User savedUser = userRepository.save(userMapper.toEntity(userCreateDTO));
        return userMapper.toDto(savedUser);
    }

    /**
     * Retrieves a user by their username. If the user is not found, a UserNotFoundException is thrown.
     * @param username
     * @return UserDTO
     * @throws UserNotFoundException
     */
    @Override
    public UserDTO getUserByUsername(String username) throws UserNotFoundException {
        // Find user by username
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));
        return userMapper.toDto(user);
    }


    /**
     *  Retrieves all users from the database and maps them to UserDTOs before returning the list.
     * @return
     */
    @Override
    public List<UserDTO> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Updates a user's password. Checks if the user exists before updating. If the user is not found, a UserNotFoundException is thrown.
     * @param username
     * @param updatePasswordDTO
     * @return
     * @throws InvalidPasswordException
     */
    @Override
    public UserDTO updateUserPassword(String username, UpdatePasswordDTO updatePasswordDTO) throws InvalidPasswordException {
        return null;
    }

    /**
     * Updates a user's email. Checks if the user exists before updating. If the user is not found, a UserNotFoundException is thrown.
     * @param username
     * @param updateEmailDTO
     * @return
     * @throws UserNotFoundException
     */
    @Override
    public UserDTO updateUserEmail(String username, UpdateEmailDTO updateEmailDTO) throws UserNotFoundException {
        return null;
    }

    /**
     * Updates a user's username. Checks if the new username is already taken and if the existing user exists before updating.
     * @param existingUsername
     * @param updateUsernameDTO
     * @return
     * @throws UserNotFoundException
     * @throws UsernameAlreadyExistsException
     */
    @Override
    public UserDTO updateUsername(String existingUsername, UpdateUsernameDTO updateUsernameDTO) throws UserNotFoundException, UsernameAlreadyExistsException {
        return null;
    }

    /**
     *  Deletes a user by their username. If the user is not found, a UserNotFoundException is thrown.
     * @param username
     * @throws UserNotFoundException
     */
    @Override
    public void deleteUser(String username) throws UserNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));
        userRepository.delete(user);
    }

}
