package br.com.lasanhaspec.carservice.service;


import br.com.lasanhaspec.carservice.domain.models.User;
import br.com.lasanhaspec.carservice.exception.ResourceNotFoundException;
import br.com.lasanhaspec.carservice.infrastructure.storage.S3StorageService;
import br.com.lasanhaspec.carservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService{



    private final UserRepository userRepository;
    private final S3StorageService storageService;

    private static final long MAX_SIZE = 5 * 1024 * 1024;

    public UserService(
            UserRepository userRepository,
            S3StorageService storageService
    ){
        this.userRepository = userRepository;
        this.storageService = storageService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }




    public  String uploadProfileImage(String email, MultipartFile file){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        validateImage(file);

        if(user.getProfileImageS3Key()!= null){
            storageService.deleteFile(user.getProfileImageS3Key());
        }

        S3StorageService.UploadResult result = storageService.uploadFile(file);

        user.setProfileImageUrl(result.getUrl());
        user.setProfileImageS3Key(result.getKey());

        userRepository.save(user);

        return result.getUrl();

    }



    private void validateImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Empty file");
        }

        if (file.getSize() > MAX_SIZE) {
            throw new IllegalArgumentException("File too large");
        }

        String contentType = file.getContentType();

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only images are allowed");
        }

        String filename = file.getOriginalFilename();

        if (filename == null ||
                !(filename.toLowerCase().endsWith(".jpg") ||
                        filename.toLowerCase().endsWith(".jpeg") ||
                        filename.toLowerCase().endsWith(".png") ||
                        filename.toLowerCase().endsWith(".webp"))) {
            throw new IllegalArgumentException("Unsupported image format");
        }
    }





}
