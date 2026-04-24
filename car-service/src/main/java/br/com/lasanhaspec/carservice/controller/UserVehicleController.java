package br.com.lasanhaspec.carservice.controller;


import br.com.lasanhaspec.carservice.dto.CreateUserVehicleDTO;
import br.com.lasanhaspec.carservice.dto.VehicleCardDTO;
import br.com.lasanhaspec.carservice.service.UserVehicleService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Long>createVehicle(@RequestBody CreateUserVehicleDTO dto){

        Long id = userVehicleService.createVehicle(dto);
        System.out.println("DTO HP: " + dto.getCurrentHorsePower());
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


    //retorna lista de veiculos do usuario
    @GetMapping()
    public ResponseEntity<List<VehicleCardDTO>> getUserVehicles(){
        return ResponseEntity.ok(userVehicleService.getUserVehicles());
    }


    @GetMapping("/{id}")
    public ResponseEntity<VehicleCardDTO> getOneUserVehicles(@PathVariable Long id){
        return ResponseEntity.ok(userVehicleService.getVehicleById(id));
        // revisar os metodos no service e repositorio
    }



    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }




    @PutMapping("/{id}")
    public ResponseEntity<VehicleCardDTO> updateVehicle(@PathVariable Long id, @RequestBody CreateUserVehicleDTO dto){
        return ResponseEntity.ok(userVehicleService.udpateVehicle(id, dto));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id){
        userVehicleService.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }




}
