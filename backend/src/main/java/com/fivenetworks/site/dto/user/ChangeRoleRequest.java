package com.fivenetworks.site.dto.user;

import com.fivenetworks.site.entity.Role;
import jakarta.validation.constraints.NotNull;

public record ChangeRoleRequest(
    @NotNull
    Role role
) {}
