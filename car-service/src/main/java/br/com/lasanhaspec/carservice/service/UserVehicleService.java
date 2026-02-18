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



    private UserVehicleRepository userVehicleRepository;
    private S3StorageService storageService;



    public String uploadVehicleImage(Long vehicleId, MultipartFile file) {

        UserVehicle vehicle = userVehicleRepository.findById(vehicleId)
                .orElseThrow();

        String url = storageService.uploadFile(file);

        VehicleImage image = new VehicleImage();
        //image.setImageUrl(url);
        //image.setUserVehicle(vehicle);
        //imageRepository.save(image);

        return url;
    }

}
