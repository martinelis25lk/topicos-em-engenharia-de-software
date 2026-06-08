package br.com.lasanhaspec.carservice.controller;


import br.com.lasanhaspec.carservice.domain.models.User;
import br.com.lasanhaspec.carservice.dto.UpdateUserDto;
import br.com.lasanhaspec.carservice.dto.UserUpdateResponseDto;
import br.com.lasanhaspec.carservice.service.JwtService;
import br.com.lasanhaspec.carservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PatchMapping("/me")
    public ResponseEntity<UserUpdateResponseDto> updateMe(
            @RequestBody UpdateUserDto request,
            Authentication authentication
    ) {
        String email = authentication.getName();
        log.info("email: {}", email);
        User updated = userService.updateUserInfo(email, request);
        return ResponseEntity.ok(toResponse(updated));
    }

    @GetMapping("/me")
    public ResponseEntity<UserUpdateResponseDto> getMe(
            Authentication authentication
    ) {
        String email = authentication.getName();

        User user = userService.getUserByEmail(email);

        return ResponseEntity.ok(toResponse(user));
    }

    @PostMapping("/me/profile-image")
    public ResponseEntity<String> UploadProfileImage(
            @RequestParam("file")MultipartFile file,
            Authentication authentication
    )
    {
      String email = authentication.getName();
      log.info("email profile change: {}", email);
      String imageUrl = userService.uploadProfileImage(email, file);
      return ResponseEntity.ok(imageUrl);
    }

    private UserUpdateResponseDto toResponse(User user) {
        return new UserUpdateResponseDto(
                user.getId(),
                user.getDisplayUsername(),
                user.getEmail(),
                user.getProfileImageUrl(),
                user.getRole().name(),
                user.getFullName()
        );
    }

}
