package com.Event.demo.controller;
import com.Event.demo.common.Role;
import com.Event.demo.user.User;
import com.Event.demo.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService)
    {
        this.userService= userService;
    }
    //1.Create User
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
    }
    //2.Get User By Id
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }
   //3. Get User by Username
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username)
    {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
    //4.Get All Users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers()
    {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    //5.Change User
    @PutMapping("/{userId}/role/{role}")
    public ResponseEntity<User> changeUserRole(
            @PathVariable long userId,
            @PathVariable String role
    ) {
        Role enumRole;
        try {
            enumRole = Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role");
        }

        return ResponseEntity.ok(userService.changeUserRole(userId, enumRole));
    }

    //6.Disable User
    @PutMapping("/{userId}/disable")
    public ResponseEntity<Void> disableUser(@PathVariable long userId ) {
        userService.disableUser(userId);
        return ResponseEntity.ok().build();
    }
    //7. Enable user
    @PutMapping("/{userId}/enable")
    public ResponseEntity<Void> enableUser(@PathVariable long userId)
    {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/scanner")
    public ResponseEntity<User> createScanner(@RequestBody User user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createScanner(user));
    }


}
