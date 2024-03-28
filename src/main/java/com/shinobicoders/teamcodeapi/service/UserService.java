package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.model.User;
import com.shinobicoders.teamcodeapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public User createUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error creating user: " + e.getMessage());
        }
    }

    public User updateUser(Long id, User userDetails) {
        // TODO: create custom exception for ResourceNotFound
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setBio(userDetails.getBio());
        user.setGithubLink(userDetails.getGithubLink());
        user.setExperience(userDetails.getExperience());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage());
        }
    }
}
