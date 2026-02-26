package br.com.lasanhaspec.carservice.controller;


import br.com.lasanhaspec.carservice.dto.CreateUserVehicleDTO;
import br.com.lasanhaspec.carservice.service.UserVehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        String imageUrl = userVehicleService.uploadVehicleImage(vehicleId, file);
        return ResponseEntity.ok(imageUrl);
    }


    @PostMapping
    public ResponseEntity<Long>createVehicle(@RequestBody CreateUserVehicleDTO dto){

        Long id = userVehicleService.createVehicle(dto);
        return ResponseEntity.ok(id);
    }


    @DeleteMapping("/vehicles/{vehicleId}/images/{imageId}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable Long vehicleId,
            @PathVariable Long imageId){
        userVehicleService.deleteVehicleImage(vehicleId, imageId);
        return ResponseEntity.noContent().build();
    }





}
