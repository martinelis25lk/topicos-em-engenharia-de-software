package br.com.lasanhaspec.carservice.mappers;

import br.com.lasanhaspec.carservice.domain.models.UserVehicle;
import br.com.lasanhaspec.carservice.dto.VehicleCardDTO;
import br.com.lasanhaspec.carservice.service.UserVehicleService;

public class VehicleCardMapper {

    public static VehicleCardDTO toDTO(UserVehicle vehicle){

        VehicleCardDTO dto = new VehicleCardDTO();

        dto.setId(vehicle.getId());
        dto.setName(vehicle.getNickname());
        dto.setFactoryHorsePower(vehicle.getVehicleCatalogModel().getFactoryHorsepower());
        dto.setCurrentHorsePower(vehicle.getCurrentHorsePower());
        dto.setFactoryTorque(vehicle.getVehicleCatalogModel().getFactoryTorque());
        dto.setCurrentTorque(vehicle.getCurrentTorque());
        dto.setEngine(vehicle.getVehicleCatalogModel().getEngineCode());
        //dto.setModificationsCount(null);
        dto.setCurrentWeight(vehicle.getCurrentWeight());
        dto.setFactoryWeight(vehicle.getVehicleCatalogModel().getFactoryWeight());

        String imageUrl = vehicle.getImages().stream()
                .filter(img -> Boolean.TRUE.equals(img.getPrimaryImage()))
                .map(img -> img.getImageUrl())
                .findFirst()
                .orElseGet(() ->
                        vehicle.getImages().stream()
                                .map(img -> img.getImageUrl())
                                .findFirst()
                                .orElse(null)
                );

        dto.setImageUrl(imageUrl);



        double gain = calculatePercentage(
                dto.getFactoryHorsePower(),
                dto.getCurrentHorsePower()
        );

        dto.setPowerGainPercentage(gain);


        return dto;

    }


    private static double calculatePercentage(int base, int current){
        if (base == 0)  return 0;
        return ((double) (current - base) / base) * 100;
    }



}
