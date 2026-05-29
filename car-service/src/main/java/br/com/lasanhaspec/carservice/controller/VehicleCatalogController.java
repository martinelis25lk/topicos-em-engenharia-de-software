package br.com.lasanhaspec.carservice.controller;


import br.com.lasanhaspec.carservice.domain.models.VehicleCatalogModel;
import br.com.lasanhaspec.carservice.dto.CreateVehicleCatalogModelDTO;
import br.com.lasanhaspec.carservice.dto.VehicleCatalogDTO;
import br.com.lasanhaspec.carservice.dto.VehiclePageDTO;
import br.com.lasanhaspec.carservice.service.VehicleCatalogService;
import br.com.lasanhaspec.carservice.service.VehiclePageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog/vehicles")
public class VehicleCatalogController {


    private final VehicleCatalogService vehicleCatalogService;
    private  final VehiclePageService vehiclePageService;





    public VehicleCatalogController(
            VehicleCatalogService vehicleCatalogService,
            VehiclePageService vehiclePageService){


        this.vehicleCatalogService = vehicleCatalogService;
        this.vehiclePageService = vehiclePageService;
    }





    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleCatalogModel createFactoryVehicle(@RequestBody VehicleCatalogModel vehicleCatalogModel){
        System.out.println("CARRRO RECEBIDO: " + vehicleCatalogModel);
        return vehicleCatalogService.save(vehicleCatalogModel);
    }

    @GetMapping
    public List<VehicleCatalogModel> List(){
        return vehicleCatalogService.findAll();
    }


    @GetMapping("/{id}")
    public VehicleCatalogModel getById(@PathVariable Long id){
        return vehicleCatalogService.findById(id);
    }



    @PutMapping("/{id}")
    public ResponseEntity<VehicleCatalogDTO> updateVehicleCatalogModel(@Valid @PathVariable Long id, @RequestBody CreateVehicleCatalogModelDTO dto ){
        return ResponseEntity.ok(vehicleCatalogService.updateVehicleCatalog(id, dto));

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicleCatlogModel(@PathVariable Long id){
        vehicleCatalogService.deleteVehicleCatalogModel(id);
        return ResponseEntity.noContent().build();
    }




}




