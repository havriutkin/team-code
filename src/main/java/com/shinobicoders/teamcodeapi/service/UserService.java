package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.model.User;
import com.shinobicoders.teamcodeapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return null;
        }

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());

        // todo: encrypt password
        user.setPassword(userDetails.getPassword());

        user.setBio(userDetails.getBio());
        user.setGithubLink(userDetails.getGithubLink());
        user.setExperience(userDetails.getExperience());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
