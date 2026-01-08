package com.Event.demo.service.user;

import com.Event.demo.user.User;
import com.Event.demo.user.UserRepository;
import com.Event.demo.common.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {

        if (user.getUserName() == null || user.getEmail() == null || user.getPassword() == null) {
            throw new RuntimeException("Username, email, and password are required");
        }

        if (userRepository.findByUserName(user.getUserName()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        user.setRole(Role.USER);
        user.setEnabled(true);

        return userRepository.save(user);
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User getUserByUsername(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("No user found with this username"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void enableUser(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void disableUser(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public User changeUserRole(long userId, Role role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(role);
        return userRepository.save(user);
    }
    @Override
    public User createScanner(User user) {
        user.setRole(Role.SCANNER);
        user.setEnabled(true);
        return userRepository.save(user);
    }

}
