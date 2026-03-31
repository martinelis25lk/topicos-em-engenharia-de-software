package br.com.lasanhaspec.carservice.service;


import br.com.lasanhaspec.carservice.domain.models.User;
import br.com.lasanhaspec.carservice.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService{



    private final UserRepository userRepository;

    public UserService(
            UserRepository userRepository
    ){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }


//

}
