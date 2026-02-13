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

    @ManyToOne(optional = false)
    private UserVehicle userVehicle;
}
