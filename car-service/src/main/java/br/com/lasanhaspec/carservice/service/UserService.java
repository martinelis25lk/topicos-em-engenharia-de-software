package br.com.lasanhaspec.carservice.service;


import br.com.lasanhaspec.carservice.domain.models.User;
import br.com.lasanhaspec.carservice.dto.UpdateUserDto;
import br.com.lasanhaspec.carservice.exception.ResourceNotFoundException;
import br.com.lasanhaspec.carservice.infrastructure.storage.S3StorageService;
import br.com.lasanhaspec.carservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Slf4j
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

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User updateUserInfo(String currentEmail, UpdateUserDto request) {
        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (request.username() != null && !request.username().isBlank()) {
            user.setUsername(request.username());
        }

        if (request.fullName() != null && !request.fullName().isBlank()) {
            user.setFullName(request.fullName());
        }

        if (request.email() != null && !request.email().isBlank()
                && !request.email().equals(currentEmail)) {

            boolean emailTaken = userRepository.findByEmail(request.email()).isPresent();
            if (emailTaken) {
                throw new IllegalArgumentException("Email already in use");
            }
            user.setEmail(request.email());
        }

        return userRepository.save(user);
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
            try {
                storageService.deleteFile(user.getProfileImageS3Key());
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }

        S3StorageService.UploadResult result = storageService.uploadFile(file);

        user.setProfileImageUrl(result.getUrl());
        user.setProfileImageS3Key(result.getKey());

        userRepository.save(user);

        return result.getUrl();

    }



    private void validateImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            log.info("Empty file");
            throw new IllegalArgumentException("Empty file");
        }

        if (file.getSize() > MAX_SIZE) {
            log.info("File too large");
            throw new IllegalArgumentException("File too large");
        }

        String contentType = file.getContentType();

        if (contentType == null || !contentType.startsWith("image/")) {
            log.info("Only images are allowed");
            throw new IllegalArgumentException("Only images are allowed");
        }

        String filename = file.getOriginalFilename();

        if (filename == null ||
                !(filename.toLowerCase().endsWith(".jpg") ||
                        filename.toLowerCase().endsWith(".jpeg") ||
                        filename.toLowerCase().endsWith(".png") ||
                        filename.toLowerCase().endsWith(".webp"))) {
            log.info("Unsupported image format");
            throw new IllegalArgumentException("Unsupported image format");
        }
    }





}
