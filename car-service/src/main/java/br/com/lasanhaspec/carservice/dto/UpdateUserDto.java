package br.com.lasanhaspec.carservice.dto;

public record UpdateUserDto(
        String username,
        String email,
        String fullName
) {}