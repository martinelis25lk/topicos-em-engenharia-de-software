package br.com.lasanhaspec.carservice.service;


import br.com.lasanhaspec.carservice.domain.enums.AspirationType;
import br.com.lasanhaspec.carservice.domain.models.VehicleCatalogModel;
import br.com.lasanhaspec.carservice.dto.CreateVehicleCatalogModelDTO;
import br.com.lasanhaspec.carservice.dto.VehicleCatalogDTO;
import br.com.lasanhaspec.carservice.exception.ResourceNotFoundException;
import br.com.lasanhaspec.carservice.repository.VehicleCatalogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleCatalogService {


    private final VehicleCatalogRepository vehicleCatalogRepository;

    public VehicleCatalogService(VehicleCatalogRepository vehicleCatalogRepository){
        this.vehicleCatalogRepository = vehicleCatalogRepository;
    }




    public VehicleCatalogModel save(VehicleCatalogModel vehicleCatalogModel){
        return vehicleCatalogRepository.save(vehicleCatalogModel);
    }


    public List<VehicleCatalogModel> findAll(){
        return vehicleCatalogRepository.findAll();
    }


    public VehicleCatalogModel findById(Long id){
        return vehicleCatalogRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("vehicleModel not found"));
    }


    public VehicleCatalogDTO updateVehicleCatalog(Long id, CreateVehicleCatalogModelDTO  dto){
        //verifica se a entidade existe

        VehicleCatalogModel vehicle = vehicleCatalogRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("vehicle catalog model not found."));


        vehicle.setBrand(dto.getBrand());
        vehicle.setEngineCode(dto.getEngineCode());
        vehicle.setFactoryHorsepower(dto.getFactoryHorsePower());
        vehicle.setAspirationType(
                AspirationType.valueOf(dto.getAspirationType())
        );
        vehicle.setFactoryWeight(dto.getFactoryWeight());
        vehicle.setFactoryTorque(dto.getFactoryTorque());

        vehicle.setFipeBrandCode(dto.getFipeBrandCode());
        vehicle.setFipeModelCode(dto.getFipeModelCode());
        vehicle.setFipeYearCode(dto.getFipeYearCode()); 


        vehicleCatalogRepository.save(vehicle);

        VehicleCatalogDTO vehicleCatalogDTO = new VehicleCatalogDTO();

        vehicleCatalogDTO.setId(vehicle.getId());
        vehicleCatalogDTO.setAspirationtype(vehicle.getAspirationType().name());
        vehicleCatalogDTO.setBrand(vehicle.getBrand());
        vehicleCatalogDTO.setYear(vehicle.getYear());
        vehicleCatalogDTO.setModel(vehicle.getModel());
        vehicleCatalogDTO.setFactoryTorque(vehicle.getFactoryTorque());
        vehicleCatalogDTO.setEngineCode(vehicle.getEngineCode());
        vehicleCatalogDTO.setFactoryHorsePower(vehicle.getFactoryHorsepower());

        return vehicleCatalogDTO;

    }



    public void deleteVehicleCatalogModel(Long id){
        VehicleCatalogModel vehicleCatalogModel = vehicleCatalogRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("vehicle catalog model not found"));

        vehicleCatalogRepository.delete(vehicleCatalogModel);


    }


}
