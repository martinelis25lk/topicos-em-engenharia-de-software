package br.com.lasanhaspec.carservice.dto;

public record UserUpdateResponseDto(
        Long id,
        String username,
        String email,
        String profileImageUrl,
        String role,
        String fullName
) {}