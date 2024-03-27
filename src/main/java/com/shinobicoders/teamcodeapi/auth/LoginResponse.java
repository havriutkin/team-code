package com.shinobicoders.teamcodeapi.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private final String token;
}
