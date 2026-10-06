package com.fivenetworks.site.dto.user;

import jakarta.validation.constraints.NotNull;

public record ChangeActiveStatusRequest (
    @NotNull
    Boolean active
) {}
