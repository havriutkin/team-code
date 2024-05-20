package com.shinobicoders.teamcodeapi.controller;

import com.shinobicoders.teamcodeapi.model.User;
import com.shinobicoders.teamcodeapi.service.AuthService;
import com.shinobicoders.teamcodeapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userService.getUserByEmail(email);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/{id}/skill/{skillId}")
    public ResponseEntity<User> addSkill(@PathVariable Long id, @PathVariable Long skillId) {
        if (!authService.authorizeUser(id)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.addSkill(id, skillId);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/{id}/skills")
    public ResponseEntity<User> addSkills(@PathVariable Long id, @RequestBody List<Long> skillIds) {
        if (!authService.authorizeUser(id)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = null;
        for (Long skillId : skillIds) {
            user = userService.addSkill(id, skillId);
        }

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {

        if(! authService.authorizeUser(id)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User updatedUser = userService.updateUser(id, userDetails);

        if (updatedUser == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/project/{projectId}")
    public ResponseEntity<User> leaveProject(@PathVariable Long userId, @PathVariable Long projectId) {
        if (!authService.authorizeUser(userId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.leaveProject(userId, projectId);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/skill/{skillId}")
    public ResponseEntity<User> removeSkill(@PathVariable Long userId, @PathVariable Long skillId) {
        if (!authService.authorizeUser(userId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = userService.removeSkill(userId, skillId);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/skills")
    public ResponseEntity<User> removeSkills(@PathVariable Long userId, @RequestBody List<Long> skillIds) {
        if (!authService.authorizeUser(userId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = null;
        for (Long skillId : skillIds) {
            user = userService.removeSkill(userId, skillId);
        }

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        // todo: check auth
        // todo: test what happens if user does not exist

        if(! authService.authorizeUser(id)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
