package com.fivenetworks.site.service;

import com.fivenetworks.site.dto.user.*;
import com.fivenetworks.site.entity.Role;
import com.fivenetworks.site.entity.User;
import com.fivenetworks.site.exception.EmailAlreadyExistsException;
import com.fivenetworks.site.exception.InvalidPasswordException;
import com.fivenetworks.site.exception.UserNotFoundException;
import com.fivenetworks.site.mapper.UserMapper;
import com.fivenetworks.site.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
            .map(userMapper::toDto)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(
                () -> new UserNotFoundException(id)
            );

        return userMapper.toDto(user);
    }

    @Transactional
    public UserDto createUser(CreateUserRequest request) {
        if(userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        User user = userMapper.toEntity(request);
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Transactional
    public UserDto changeEmail(Long id, ChangeEmailRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(
                () -> new UserNotFoundException(id)
            );

        if(!user.getEmail().equalsIgnoreCase(request.email())
        && userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        user.setEmail(request.email());
        return userMapper.toDto(user);
    }

    @Transactional
    public UserDto changeFullName(Long id, ChangeFullNameRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(
                () -> new UserNotFoundException(id)
            );
        user.setFullName(request.fullName());
        return userMapper.toDto(user);
    }

    @Transactional
    public UserDto changeRole(Long id, ChangeRoleRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(
                () -> new UserNotFoundException(id)
            );
        user.setRole(request.role());
        return userMapper.toDto(user);
    }

    @Transactional
    public void changePassword(Long id, ChangePasswordRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(
                () -> new UserNotFoundException(id)
            );
        String current = request.currentPassword();
        String hash = user.getPasswordHash();

        if(!passwordEncoder.matches(current, hash)) {
            throw new InvalidPasswordException();
        }

        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
    }

    @Transactional
    public UserDto changeActiveStatus(Long id, ChangeActiveStatusRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(
                () -> new UserNotFoundException(id)
            );
        user.setActive(request.active());
        return userMapper.toDto(user);
    }
}
