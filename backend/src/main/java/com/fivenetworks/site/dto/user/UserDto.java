package com.fivenetworks.site.dto.user;

import com.fivenetworks.site.entity.Role;

import java.time.Instant;

public record UserDto(
    Long id,
    String email,
    String fullName,
    Role role,
    Boolean active,
    Instant createdAt,
    Instant updatedAt
) {}
