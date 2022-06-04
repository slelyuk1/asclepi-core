package com.jupiter.asclepi.core.model.request.security;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuthenticationRequest {
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
}
