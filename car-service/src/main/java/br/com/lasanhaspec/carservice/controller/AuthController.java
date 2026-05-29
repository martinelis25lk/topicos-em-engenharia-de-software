package br.com.lasanhaspec.carservice.controller;


import br.com.lasanhaspec.carservice.domain.enums.Role;
import br.com.lasanhaspec.carservice.domain.models.User;
import br.com.lasanhaspec.carservice.dto.RegisterRequestDTO;
import br.com.lasanhaspec.carservice.repository.UserRepository;
import br.com.lasanhaspec.carservice.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDTO dto) {


        //1 verifica se email já existe

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("EMAIL JÁ CADASTRADO");
        }


        //2 cria o usuário
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // hasheia o password
        user.setRole(Role.ROLE_USER);


        //3 salva no banco

        userRepository.save(user);



        // 4 gera o token
        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(token);

    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody RegisterRequestDTO dto) {


        //autentica, lança excecao automaticamente se credenciais invalidas

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword())

        );

        //busca o usuário

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow();


        //gera e retorna o token
        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(token);


    }


}