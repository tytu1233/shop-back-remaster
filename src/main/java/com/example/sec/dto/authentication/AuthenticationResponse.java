package com.example.sec.dto.authentication;

public record AuthenticationResponse(String accessJwtToken, String refreshJwtToken) {}