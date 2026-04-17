package com.flowers.springsecurity.dtos;

import lombok.Builder;

@Builder
public record AuthenticationRequest(String email, String password) {
}
