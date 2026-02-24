package br.com.lasanhaspec.carservice.controller;


import br.com.lasanhaspec.carservice.service.UserVehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController("user-vehicle-controller")
@RequestMapping("/vehicles")
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



}
