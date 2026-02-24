package br.com.lasanhaspec.carservice.service;


import br.com.lasanhaspec.carservice.domain.models.UserVehicle;
import br.com.lasanhaspec.carservice.domain.models.VehicleImage;
import br.com.lasanhaspec.carservice.infrastructure.storage.S3StorageService;
import br.com.lasanhaspec.carservice.repository.SetupRepository;
import br.com.lasanhaspec.carservice.repository.UserVehicleRepository;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserVehicleService {



    private final UserVehicleRepository userVehicleRepository;
    private final S3StorageService storageService;

    public UserVehicleService(UserVehicleRepository userVehicleRepository, S3StorageService storageService){
        this.userVehicleRepository = userVehicleRepository;
        this.storageService = storageService;
    }



    public String uploadVehicleImage(Long vehicleId, MultipartFile file) {

        UserVehicle vehicle = userVehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("vehicle not found UserVehicleService"));

        String url = storageService.uploadFile(file);

        // TODO: salvar entidade VehicleImage


        return url;
    }

}
