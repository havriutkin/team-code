package com.shinobicoders.teamcodeapi.service;

import com.shinobicoders.teamcodeapi.exception.EntityCreationException;
import com.shinobicoders.teamcodeapi.exception.EntityDeletionException;
import com.shinobicoders.teamcodeapi.exception.EntityUpdateException;
import com.shinobicoders.teamcodeapi.model.User;
import com.shinobicoders.teamcodeapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUserById(long id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    public User getUserByEmail(String email) throws NotFoundException{
        return userRepository.findByEmail(email)
                .orElseThrow(NotFoundException::new);
    }

    public User createUser(User user) {
        try{
            return userRepository.save(user);
        } catch (Exception e){
            throw new EntityCreationException("Error creating user: " + e.getMessage(), e.getCause());
        }
    }

    public User updateUser(Long id, User userDetails) throws NotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setBio(userDetails.getBio());
        user.setGithubLink(userDetails.getGithubLink());
        user.setExperience(userDetails.getExperience());

        try {
            return userRepository.save(user);
        } catch (Exception e){
            throw new EntityUpdateException("Error updating user: " + e.getMessage(), e.getCause());
        }
    }

    public void deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new EntityDeletionException("Error deleting user: " + e.getMessage(), e.getCause());
        }
    }
}
