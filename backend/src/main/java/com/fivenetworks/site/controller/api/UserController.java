package com.fivenetworks.site.controller.api;

import com.fivenetworks.site.dto.user.*;
import com.fivenetworks.site.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(
        @Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @PatchMapping("/{id}/email")
    public UserDto changeEmail(
        @PathVariable Long id,
        @Valid @RequestBody ChangeEmailRequest request) {
        return userService.changeEmail(id, request);
    }

    @PatchMapping("/{id}/full-name")
    public UserDto changeFullName(
        @PathVariable Long id,
        @Valid @RequestBody ChangeFullNameRequest request) {
        return userService.changeFullName(id, request);
    }

    @PatchMapping("/{id}/role")
    public UserDto changeRole(
        @PathVariable Long id,
        @Valid @RequestBody ChangeRoleRequest request){
        return userService.changeRole(id, request);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<String> changePassword(
        @PathVariable Long id,
        @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(id, request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/active")
    public UserDto changeActiveStatus(
        @PathVariable Long id,
        @Valid @RequestBody ChangeActiveStatusRequest request
    ) {
        return userService.changeActiveStatus(id, request);
    }
}
