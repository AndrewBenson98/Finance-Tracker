package com.benson.user_service.controller;

import com.benson.user_service.models.dto.request.*;
import com.benson.user_service.models.dto.response.UserDTO;
import com.benson.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class UserController {


    private final UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {

        UserDTO user = userService.createUser(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {

        return ResponseEntity.ok(userService.getAllUsers());

    }

    @GetMapping("/users/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@RequestParam Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/users/{username}")
    public ResponseEntity<UserDTO> deleteUserByUsername(@RequestParam String username) {
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

//Disabling Update endpoints for now
//    @PutMapping("/users/{existingUsername}/update-username")
//    public ResponseEntity<UserDTO> updateUsername(@RequestParam String existingUsername, @RequestBody UpdateUsernameDTO updateUsernameDTO) {
//        return ResponseEntity.ok(userService.updateUsername(existingUsername, updateUsernameDTO));
//    }

//    @PutMapping("/users/{username}/update-email")
//    public ResponseEntity<UserDTO> updateUserEmail(@RequestParam String username, @RequestBody UpdateEmailDTO updateEmailDTO) {
//        return ResponseEntity.ok(userService.updateUserEmail(username, updateEmailDTO));
//    }
//
//    @PutMapping("/users/{username}/update-password")
//    public ResponseEntity<UserDTO> updateUserPassword(@RequestParam String username, @RequestBody UpdatePasswordDTO updatePasswordDTO) {
//        return ResponseEntity.ok(userService.updateUserPassword(username, updatePasswordDTO));
//    }
//
    @PostMapping("/users/authenticate")
    public ResponseEntity<UserDTO> authenticateUser(@RequestBody UserLoginDTO userLoginDTO) {
        return ResponseEntity.ok(userService.authenticateUser(userLoginDTO));
    }


}
