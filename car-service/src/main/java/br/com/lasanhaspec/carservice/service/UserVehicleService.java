package br.com.lasanhaspec.carservice.service;


import br.com.lasanhaspec.carservice.domain.models.UserVehicle;
import br.com.lasanhaspec.carservice.domain.models.VehicleCatalogModel;
import br.com.lasanhaspec.carservice.domain.models.VehicleImage;
import br.com.lasanhaspec.carservice.dto.CreateUserVehicleDTO;
import br.com.lasanhaspec.carservice.infrastructure.storage.S3StorageService;
import br.com.lasanhaspec.carservice.repository.UserVehicleRepository;
import br.com.lasanhaspec.carservice.repository.VehicleCatalogRepository;
import br.com.lasanhaspec.carservice.repository.VehicleImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserVehicleService {



    private final UserVehicleRepository userVehicleRepository;
    private final VehicleImageRepository vehicleImageRepository;
    private final S3StorageService storageService;
    private final VehicleCatalogRepository vehicleCatalogRepository;


    private static final long MAX_SIZE = 5 * 1024 * 1024; // 5mb



    public UserVehicleService(
            UserVehicleRepository userVehicleRepository,
            S3StorageService storageService,
            VehicleCatalogRepository vehicleCatalogRepository,
            VehicleImageRepository vehicleImageRepository
            ){
        this.userVehicleRepository = userVehicleRepository;
        this.storageService = storageService;
        this.vehicleCatalogRepository = vehicleCatalogRepository;
        this.vehicleImageRepository = vehicleImageRepository;
    }



    public String uploadVehicleImage(Long vehicleId, MultipartFile file) {





        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo vazio");
        }

        if (file.getSize() > MAX_SIZE) {
            throw new IllegalArgumentException("Arquivo muito grande");
        }

        String contentType = file.getContentType();

        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Apenas imagens são permitidas");
        }

        String filename = file.getOriginalFilename();

        if (filename == null ||
                !(filename.toLowerCase().endsWith(".jpg") ||
                        filename.endsWith(".jpeg") ||
                        filename.endsWith(".png") ||
                        filename.endsWith(".webp"))) {
            throw new IllegalArgumentException("Formato de imagem não suportado");
        }



        UserVehicle vehicle = userVehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("vehicle not found UserVehicleService KKKKK"));

        String url = storageService.uploadFile(file);

        VehicleImage image = new VehicleImage();
        image.setUserVehicle(vehicle);
        image.setImageUrl(url);
        image.setS3Key(filename);

        //vehicleImageRepository.save(image);

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


    public void deleteVehicleImage(Long vehicleId, Long imageId){
        VehicleImage image = vehicleImageRepository.findById(imageId)
                .orElseThrow(()-> new RuntimeException("Image not found, kkk uservehicle service"));


        if (image.getUserVehicle() == null ||
                !image.getUserVehicle().getId().equals(vehicleId)) {
            throw new RuntimeException("Image does not belong to vehicle");
        }

        storageService.deleteFile(image.getS3Key());
        vehicleImageRepository.delete(image);

    }

}


//arn:aws:iam::697220083264:user/lasanhaspec-apilasanhaspec-api