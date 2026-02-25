package br.com.lasanhaspec.carservice.domain.models;


import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Table(name = "vehicle_images")
public class VehicleImage {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    private Boolean primaryImage;

    private String s3Key;

    @ManyToOne(optional = false)
    private UserVehicle userVehicle;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getPrimaryImage() {
        return primaryImage;
    }

    public void setPrimaryImage(Boolean primaryImage) {
        this.primaryImage = primaryImage;
    }

    public String getS3Key() {
        return s3Key;
    }

    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }

    public UserVehicle getUserVehicle() {
        return userVehicle;
    }

    public void setUserVehicle(UserVehicle userVehicle) {
        this.userVehicle = userVehicle;
    }
}
