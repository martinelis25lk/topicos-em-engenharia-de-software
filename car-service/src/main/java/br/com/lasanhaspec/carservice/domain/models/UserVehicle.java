package br.com.lasanhaspec.carservice.domain.models;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user_vehicles")
public class UserVehicle {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId; // vai virar relacionamento

    @ManyToOne(optional = false)
    private VehicleCatalogModel vehicleCatalogModel;

    private String nickname; // exemplo: minha e36 turbo

    private Boolean active = true;

    @OneToMany(mappedBy = "UserVehicle", cascade = CascadeType.ALL)
    private List<CurrentSetup>setups;
}

// essa entidade n vai guardar potencia, ou modificação alguma, eh so pra representar o carro base do usuário.
