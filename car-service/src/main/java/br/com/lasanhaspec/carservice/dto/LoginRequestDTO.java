package br.com.lasanhaspec.carservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequestDTO {
    @NotBlank
    @Email
    @Size(max = 1200)
    private String email;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;

    public @NotBlank @Email @Size(max = 1200) String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email @Size(max = 1200) String email) {
        this.email = email;
    }

    public @NotBlank @Size(min = 6, max = 100) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 6, max = 100) String password) {
        this.password = password;
    }
}
