package br.com.lasanhaspec.carservice.repository;

import br.com.lasanhaspec.carservice.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);
}
