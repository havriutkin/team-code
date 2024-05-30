package com.shinobicoders.teamcodeapi.controller;

import com.shinobicoders.teamcodeapi.model.Project;
import com.shinobicoders.teamcodeapi.model.User;
import com.shinobicoders.teamcodeapi.service.AuthService;
import com.shinobicoders.teamcodeapi.service.ProjectService;
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
    private final ProjectService projectService;

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

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<User>> getUsersByProjectId(@PathVariable Long projectId) {
        if (!authService.authorizeProjectOwner(projectId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Project project = projectService.getProjectById(projectId);
        List<User> users = project.getParticipants();

        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> isUserExistsByEmail(@PathVariable String email) {
        try {
            boolean exists = userService.isUserExistsByEmail(email);
            if (exists){
                return new ResponseEntity<>(HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/exists/name/{username}")
    public ResponseEntity<Boolean> isUserExistsByName(@PathVariable String username) {
        try {
            boolean exists = userService.isUserExistsByName(username);
            if (exists){
                return new ResponseEntity<>(HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
        if(! authService.authorizeUser(id)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
