package com.fivenetworks.site.dto.user;

import com.fivenetworks.site.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest (
    @Email @NotBlank @Size(max = 255)
    String email,
    @NotBlank @Size(min = 8,max = 72)
    String password,
    @NotBlank @Size(max = 255)
    String fullName,
    @NotNull
    Role role
){}
