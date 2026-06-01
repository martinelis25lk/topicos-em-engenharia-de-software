package br.com.lasanhaspec.carservice.controller;


import br.com.lasanhaspec.carservice.service.JwtService;
import br.com.lasanhaspec.carservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UserController {


    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }



    @PostMapping("/me/profile-image")
    public ResponseEntity<String> UploadProfileImage(
            @RequestParam("file")MultipartFile file,
            Authentication authentication
    )
    {
      String email = authentication.getName();
      String imageUrl = userService.uploadProfileImage(email, file);
      return ResponseEntity.ok(imageUrl);
    }


}
