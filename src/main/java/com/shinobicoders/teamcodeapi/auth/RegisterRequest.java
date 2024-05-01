package com.shinobicoders.teamcodeapi.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterRequest {
    private final String name;
    private final String email;
    private final String password;
}
