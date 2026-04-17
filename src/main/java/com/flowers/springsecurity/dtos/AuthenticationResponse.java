package com.flowers.springsecurity.dtos;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String token) {
}
