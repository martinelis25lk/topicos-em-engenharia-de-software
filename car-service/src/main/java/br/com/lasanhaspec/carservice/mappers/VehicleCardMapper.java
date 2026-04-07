package br.com.lasanhaspec.carservice.mappers;

import br.com.lasanhaspec.carservice.domain.models.UserVehicle;
import br.com.lasanhaspec.carservice.domain.models.VehicleCatalogModel;
import br.com.lasanhaspec.carservice.dto.VehicleCardDTO;
import br.com.lasanhaspec.carservice.service.UserVehicleService;



public class VehicleCardMapper {

    public static VehicleCardDTO toDTO(UserVehicle vehicle){

        VehicleCardDTO dto = new VehicleCardDTO();




        dto.setId(vehicle.getId());
        dto.setName(vehicle.getNickname());

        VehicleCatalogModel model = vehicle.getVehicleCatalogModel();

        dto.setFactoryHorsePower(vehicle.getVehicleCatalogModel().getFactoryHorsepower());
        dto.setCurrentHorsePower(vehicle.getCurrentHorsePower());
        dto.setFactoryTorque(vehicle.getVehicleCatalogModel().getFactoryTorque());
        dto.setCurrentTorque(vehicle.getCurrentTorque());
        dto.setEngine(vehicle.getVehicleCatalogModel().getEngineCode());
        //dto.setModificationsCount(null);
        dto.setCurrentWeight(vehicle.getCurrentWeight());
        dto.setFactoryWeight(vehicle.getVehicleCatalogModel().getFactoryWeight());

        //calculo dos ganhos ou perdas de cv, torque e peso
        dto.setHorsepowerDiff(safeDiff(dto.getCurrentHorsePower() , dto.getFactoryHorsePower()));
        dto.setTorqueDiff(safeDiff(dto.getCurrentTorque() ,dto.getFactoryTorque()));
        dto.setWeightDiff(safeDiff(dto.getCurrentWeight() , dto.getFactoryWeight()));
        //


        dto.setDisplacement(vehicle.getVehicleCatalogModel().getDisplacement());
        dto.setAcceleration0to100(vehicle.getVehicleCatalogModel().getAcceleration0to100());

        //enums


        dto.setTransmissionModel(
                model.getTransmissionModel() != null ? model.getTransmissionModel().name() : null
        );
        dto.setDriveType(
                model.getDriveType() != null ? model.getDriveType().name() : null
        );







        //tendencias pras barras
        dto.setHorsepowerTrend(getTrend(dto.getHorsepowerDiff(), true));
        dto.setTorqueTrend(getTrend(dto.getTorqueDiff(), true));
        dto.setWeightTrend(getTrend(dto.getWeightDiff(), false));

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


    private static int safeDiff(Integer current, Integer factory) {
        if (current == null || factory == null) return 0;
        return current - factory;
    }


    private static String getTrend(int diff, boolean positiveIsGood) {

        if (diff == 0) {
            return "NEUTRAL";
        }

        if (positiveIsGood) {
            if (diff > 0) {
                return "POSITIVE";
            } else {
                return "NEGATIVE";
            }
        } else {
            if (diff < 0) {
                return "POSITIVE";
            } else {
                return "NEGATIVE";
            }
        }
    }


}
