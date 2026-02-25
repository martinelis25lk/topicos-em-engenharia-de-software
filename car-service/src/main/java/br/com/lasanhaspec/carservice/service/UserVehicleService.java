package br.com.lasanhaspec.carservice.service;


import br.com.lasanhaspec.carservice.domain.models.UserVehicle;
import br.com.lasanhaspec.carservice.domain.models.VehicleCatalogModel;
import br.com.lasanhaspec.carservice.domain.models.VehicleImage;
import br.com.lasanhaspec.carservice.dto.CreateUserVehicleDTO;
import br.com.lasanhaspec.carservice.infrastructure.storage.S3StorageService;
import br.com.lasanhaspec.carservice.repository.SetupRepository;
import br.com.lasanhaspec.carservice.repository.UserVehicleRepository;
import br.com.lasanhaspec.carservice.repository.VehicleCatalogRepository;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserVehicleService {



    private final UserVehicleRepository userVehicleRepository;
    private final S3StorageService storageService;
    private final VehicleCatalogRepository vehicleCatalogRepository;



    public UserVehicleService(
            UserVehicleRepository userVehicleRepository,
            S3StorageService storageService,
            VehicleCatalogRepository vehicleCatalogRepository
            ){
        this.userVehicleRepository = userVehicleRepository;
        this.storageService = storageService;
        this.vehicleCatalogRepository = vehicleCatalogRepository;
    }



    public String uploadVehicleImage(Long vehicleId, MultipartFile file) {

        UserVehicle vehicle = userVehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("vehicle not found UserVehicleService KKKKK"));

        String url = storageService.uploadFile(file);

        // TODO: salvar entidade VehicleImage


        return url;
    }



    public Long createVehicle(CreateUserVehicleDTO dto){
        VehicleCatalogModel model = vehicleCatalogRepository
                .findById(dto.getVehicleCatalogModelId())
                .orElseThrow(() ->
                        new RuntimeException("Catalog vehiclke not found, user vehicle service kkkkk"));


        UserVehicle vehicle = new UserVehicle();
        vehicle.setUserId(dto.getUserId());
        vehicle.setVehicleCatalogModel(model);
        vehicle.setNickname(dto.getNickName());
        vehicle.setActive(true);

        return userVehicleRepository.save(vehicle).getId();

    }

}


//arn:aws:iam::697220083264:user/lasanhaspec-apilasanhaspec-api