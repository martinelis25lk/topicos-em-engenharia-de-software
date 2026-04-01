package br.com.lasanhaspec.carservice.service;


import br.com.lasanhaspec.carservice.domain.models.UserVehicle;
import br.com.lasanhaspec.carservice.domain.models.VehicleCatalogModel;
import br.com.lasanhaspec.carservice.domain.models.VehicleImage;
import br.com.lasanhaspec.carservice.dto.CreateUserVehicleDTO;
import br.com.lasanhaspec.carservice.dto.VehicleCardDTO;
import br.com.lasanhaspec.carservice.infrastructure.storage.S3StorageService;
import br.com.lasanhaspec.carservice.mappers.VehicleCardMapper;
import br.com.lasanhaspec.carservice.repository.UserVehicleRepository;
import br.com.lasanhaspec.carservice.repository.VehicleCatalogRepository;
import br.com.lasanhaspec.carservice.repository.VehicleImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;



import java.util.List;

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


        UserVehicle vehicle = userVehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("vehicle not found UserVehicleService KKKKK"));

        long count = vehicleImageRepository.countByUserVehicleId(vehicleId);

        if (count >= 5) {
            throw new RuntimeException("Only 5 images per vehicle");
        }



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


        S3StorageService.UploadResult result = storageService.uploadFile(file);


        VehicleImage image = new VehicleImage();
        image.setUserVehicle(vehicle);
        image.setImageUrl(result.getUrl());
        image.setS3Key(result.getKey());

       // boolean isFirstImage = vehicle.getImages().isEmpty();
       // image.setPrimaryImage(isFirstImage);

        boolean hasImages = vehicleImageRepository.existsByUserVehicleId(vehicleId);
        image.setPrimaryImage(!hasImages);


        vehicleImageRepository.save(image);
        System.out.println("SALVOU IMAGEM NO BANCO");

        return result.getUrl();
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
        vehicle.setCurrentHorsePower(dto.getCurrentHorsePower());
        vehicle.setCurrentWeight(dto.getCurrentWeight());
        vehicle.setCurrentTorque(dto.getCurrentTorque());

        System.out.println("ENTITY HP: " + vehicle.getCurrentHorsePower());

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


    public List<VehicleCardDTO> getUserVehicles(){
        return userVehicleRepository.findAll()
                .stream()
                .map(VehicleCardMapper::toDTO)
                .toList();
    }


    public VehicleCardDTO getVehicleById(Long id) {
        return userVehicleRepository.findById(id)
                .map(VehicleCardMapper::toDTO)
                .orElseThrow(()-> new RuntimeException("vehicle not found"));
    }


    @Transactional
    public void setPrimaryImage(Long vehicleId, Long imageId) {



        //buscar imagem
        VehicleImage image = new VehicleImage();
        image = vehicleImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("image not found kk vehcileservice"));

        System.out.println("TESTES HHHHHH service DE PRIMARY");

        //valida se petence ao veiculo
        if(!image.getUserVehicle().getId().equals(vehicleId)){
            throw new RuntimeException("this image does not belong to the vehicle");

        }

        //remove primary das outras
        vehicleImageRepository.clearPrimaryByVehicle(vehicleId);



        image.setPrimaryImage(true);

        vehicleImageRepository.save(image);


    }



    public String getPrimaryImageUrl(Long vehicleId){
        VehicleImage image_url = new VehicleImage();



        return vehicleImageRepository
                .findByUserVehicleIdAndPrimaryImageTrue(vehicleId)
                .map(VehicleImage::getImageUrl)
                .orElse(null);

    }



}


//arn:aws:iam::697220083264:user/lasanhaspec-apilasanhaspec-api