package br.com.lasanhaspec.carservice.controller;


import br.com.lasanhaspec.carservice.dto.CreateUserVehicleDTO;
import br.com.lasanhaspec.carservice.dto.VehicleCardDTO;
import br.com.lasanhaspec.carservice.service.UserVehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/user-vehicles")
public class UserVehicleController {

    private final UserVehicleService userVehicleService;

    public UserVehicleController(UserVehicleService userVehicleService){
        this.userVehicleService = userVehicleService;
    }


    @PostMapping("/{vehicleId}/images")
    public ResponseEntity<String> uploadImage(
            @PathVariable Long vehicleId,
            @RequestParam("file") MultipartFile file
    ) {

        System.out.println("CHEGOU NO CONTROLLER");
        String imageUrl = userVehicleService.uploadVehicleImage(vehicleId, file);
        System.out.println("CHEGOU NO CONTROLLER");
        return ResponseEntity.ok(imageUrl);
    }



    @PutMapping("/{vehicleId}/images/{imageId}/primary")
    public ResponseEntity<Void> setPrimaryImage(
            @PathVariable Long vehicleId,
            @PathVariable Long imageId
    ) {
        userVehicleService.setPrimaryImage(vehicleId, imageId);
        return ResponseEntity.noContent().build();
    }


    @PostMapping
    public ResponseEntity<Long>createUserVehicle(
            @RequestBody CreateUserVehicleDTO dto,
            Authentication authentication){

        String email = authentication.getName();
        Long id = userVehicleService.createUserVehicle(dto, email);
        return ResponseEntity.ok(id);
    }


    @PostMapping("/teste")
    public String teste() {
        System.out.println("BATEU NO TESTE");
        return "ok";
    }


    @DeleteMapping("/vehicles/{vehicleId}/images/{imageId}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable Long vehicleId,
            @PathVariable Long imageId){
        userVehicleService.deleteVehicleImage(vehicleId, imageId);
        return ResponseEntity.noContent().build();
    }


    //retorna lista de veiculos cadastrados em todas as garagens
    @GetMapping()
    public ResponseEntity<List<VehicleCardDTO>> getUserVehicles(){
        return ResponseEntity.ok(userVehicleService.getUserVehicles());
    }



    @GetMapping("/me")
    public ResponseEntity<List<VehicleCardDTO>> getMyVehicles(Authentication authentication) {
        String email = authentication.getName();

        return ResponseEntity.ok(
                userVehicleService.getVehiclesFromAuthenticatedUser(email)
        );
    }



    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }




    @PutMapping("/{id}")
    public ResponseEntity<VehicleCardDTO> updateVehicle(
            @PathVariable Long id,
            @RequestBody CreateUserVehicleDTO dto,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return ResponseEntity.ok(
                userVehicleService.updateVehicleFromAuthenticatedUser(id, dto, email)
        );
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String email = authentication.getName();

        userVehicleService.deleteVehicleFromAuthenticatedUser(id, email);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{vehicleId}")
    public ResponseEntity<VehicleCardDTO> getOneUserVehicle(
            @PathVariable Long vehicleId,
            Authentication authentication
    ) {
        String email = authentication.getName();

        return ResponseEntity.ok(
                userVehicleService.getVehicleByIdForAuthenticatedUser(vehicleId, email)
        );
    }




}
