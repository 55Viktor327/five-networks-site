package com.fivenetworks.site.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangeFullNameRequest(
    @NotBlank @Size(max = 255)
    String fullName
) {}
