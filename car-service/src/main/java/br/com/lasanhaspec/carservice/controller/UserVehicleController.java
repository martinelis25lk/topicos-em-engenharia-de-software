package br.com.lasanhaspec.carservice.controller;


import br.com.lasanhaspec.carservice.service.UserVehicleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller("user-vehicle-controller")
public class UserVehicleController {

    UserVehicleService userVehicleService;



    @PostMapping("/{vehicleId}/images")
    public ResponseEntity<String> uploadImage(
            @PathVariable Long vehicleId,
            @RequestParam MultipartFile file
    ) {
        String imageUrl = userVehicleService.uploadVehicleImage(vehicleId, file);
        return ResponseEntity.ok(imageUrl);
    }






}
