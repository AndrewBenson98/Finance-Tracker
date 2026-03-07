package com.benson.user_service.service;

import com.benson.user_service.exceptions.InvalidPasswordException;
import com.benson.user_service.exceptions.UserAlreadyExistsException;
import com.benson.user_service.exceptions.UserNotFoundException;
import com.benson.user_service.exceptions.UsernameAlreadyExistsException;
import com.benson.user_service.models.User;
import com.benson.user_service.models.dto.request.*;
import com.benson.user_service.models.dto.response.UserDTO;
import com.benson.user_service.repository.UserRepository;
import com.benson.user_service.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(@Autowired UserRepository userRepository, @Autowired UserMapper userMapper, @Autowired PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates a new user in the system. Checks if the username is already taken before saving to the database.
     * @param userCreateDTO
     * @return
     * @throws UserAlreadyExistsException
     */
    @Override
    public UserDTO createUser(UserCreateDTO userCreateDTO) throws UserAlreadyExistsException {

        userRepository.findByUsername(userCreateDTO.username() ).ifPresent(u -> {
            throw new UserAlreadyExistsException("Username " + userCreateDTO.username() + " is taken");
        });

        //Map to entity
        User user = userMapper.toEntity(userCreateDTO);

        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //Save entity to database
        User savedUser = userRepository.save(user);

        // Map saved entity to DTO and return
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

        //check that user exists
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));

        //Hash the existing password and compare with the provided current password
        if (!passwordEncoder.matches(updatePasswordDTO.existingPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Existing password is incorrect");
        }

        //Hash the new password and update the user entity
        user.setPassword(passwordEncoder.encode(updatePasswordDTO.newPassword()));

        //Save the updated user entity to the database
        User updatedUser = userRepository.save(user);

        // Map the updated user entity to a UserDTO and return
        return userMapper.toDto(updatedUser);

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

        //check that user exists
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with username " + username + " not found"));

        // Update the user's email and save the updated user entity to the database
        user.setEmail(updateEmailDTO.newEmail());
        User updatedUser = userRepository.save(user);

        // Map the updated user entity to a UserDTO and return
        return userMapper.toDto(updatedUser);

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

        //check that user exists
        User user = userRepository.findByUsername(existingUsername).orElseThrow(() -> new UserNotFoundException("User with username " + existingUsername + " not found"));

        //check that new username is not taken
        userRepository.findByUsername(updateUsernameDTO.newUsername()).ifPresent(u -> {
            throw new UsernameAlreadyExistsException("Username " + updateUsernameDTO.newUsername() + " is taken");
        });

        //Update the user's username and save the updated user entity to the database
        user.setUsername(updateUsernameDTO.newUsername());
        User updatedUser = userRepository.save(user);

        // Map the updated user entity to a UserDTO and return
        return userMapper.toDto(updatedUser);

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

    @Override
    public UserDTO authenticateUser(UserLoginDTO userLoginDTO) throws UserNotFoundException, InvalidPasswordException {

        //Check that user exists
        User user = userRepository.findByUsername(userLoginDTO.username()).orElseThrow(() -> new UserNotFoundException("User with username " + userLoginDTO.username() + " not found"));

        //Check that provided password matches the stored hashed password
        if (!passwordEncoder.matches(userLoginDTO.password(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid Username or Password");
        }

        // If authentication is successful, return the user details as a UserDTO
        return userMapper.toDto(user);

    }

}
