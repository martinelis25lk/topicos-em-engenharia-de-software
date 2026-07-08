package br.com.lasanhaspec.carservice.controller;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import br.com.lasanhaspec.carservice.domain.models.User;
import br.com.lasanhaspec.carservice.infrastructure.storage.*;


import br.com.lasanhaspec.carservice.model.*;
import br.com.lasanhaspec.carservice.service.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/multimedia")
@CrossOrigin(origins = "http://localhost:5173") // Libera o acesso para o Vite do Front-end
public class MultiMediaController {

    private final MidiaService midiaService;
    private final ImagemService imagemService;
    private final VideoService videoService;
    private final AudioService audioService;
    private final S3StorageService s3StorageService;

    // Injeção de dependência dos seus Services através do construtor do Spring
    public MultiMediaController(MidiaService midiaService, ImagemService imagemService, 
                                VideoService videoService, AudioService audioService, 
                                S3StorageService s3StorageService) {
        this.midiaService = midiaService;
        this.imagemService = imagemService;
        this.videoService = videoService;
        this.audioService = audioService;
        this.s3StorageService = s3StorageService;
    }

    //ROTA IMAGEM
    @PostMapping(value = "/imagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Midia> cadastrarImagem(
            @RequestParam("nome") String nome,
            @RequestParam("file") MultipartFile file,
            Authentication authentication) throws IOException {
        File tempFile = convertMultipartToFile(file);
        String usernameLogado = authentication.getName();
        Imagem imagem = new Imagem();
        imagem.setNome(nome);
        imagem.setCaminhoArquivo(tempFile.getAbsolutePath());
        imagem.setDataCadastro(LocalDateTime.now());
        imagem.setUsername(usernameLogado);
        Midia salva = imagemService.cadastrar(imagem);
        System.out.println("MÍDIA SALVA");
        
        tempFile.delete(); 

        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    /**
     * 2. CADASTRAR/IMPORTAR UM ÁUDIO
     */
    @PostMapping(value = "/audio", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Midia> cadastrarAudio(
            @RequestParam("nome") String nome,
            @RequestParam("file") MultipartFile file,
            Authentication authentication) throws IOException {

        File tempFile = convertMultipartToFile(file);

        Audio audio = new Audio();
        audio.setNome(nome);
        audio.setCaminhoArquivo(tempFile.getAbsolutePath());
        String usernameLogado = authentication.getName();

        audio.setDataCadastro(LocalDateTime.now());
        audio.setUsername(usernameLogado);
        Midia salva = audioService.cadastrar(audio);
        tempFile.delete();

        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    /**
     * 3. CADASTRAR/IMPORTAR UM VÍDEO
     */
    @PostMapping(value = "/video", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> cadastrarVideo(
            @RequestParam("nome") String nome,
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {

        try {
            File tempFile = convertMultipartToFile(file);

            Video video = new Video();
            video.setNome(nome);
            video.setCaminhoArquivo(tempFile.getAbsolutePath());
            video.setDataCadastro(LocalDateTime.now());
        String usernameLogado = authentication.getName();

        video.setDataCadastro(LocalDateTime.now());
        video.setUsername(usernameLogado);
            Midia salva = videoService.cadastrar(video);
            tempFile.delete();

            return ResponseEntity.status(HttpStatus.CREATED).body(salva);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar vídeo: " + e.getMessage());
        }
    }

    /**
     * 4. LISTAR TODAS AS MÍDIAS DO BANCO
     */
   /**
     * 4. LISTAR TODAS AS MÍDIAS DO BANCO (Filtrado por Usuário)
     */
    @GetMapping
    public ResponseEntity<List<Midia>> listarMidias(Authentication authentication) {
        // 1. Segurança: Garante que a requisição está autenticada
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 2. Captura o e-mail/username direto do token JWT validado
        String usernameLogado = authentication.getName();
        System.out.println("Buscando mídias pertencentes ao username: " + usernameLogado);

        // 3. Aciona o Service passando o filtro do usuário logado
        List<Midia> midias = midiaService.listarPorUsername(usernameLogado);
        
        return ResponseEntity.ok(midias);
    }
    /**
     * 5. BUSCAR MÍDIA POR ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Midia> buscarPorId(@PathVariable int id) {
        Midia midia = midiaService.buscarPorId(id, ""); // Aqui você pode passar o username se quiser filtrar por usuário
        if (midia != null) {
            return ResponseEntity.ok(midia);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * 6. REMOVER MÍDIA DO S3 PELA CHAVE
     * Passado na URL como query param igual ao seu Scanner (ex: /delete?chaveS3=nome_do_arquivo.jpg)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarMidia(@PathVariable("id") Long id, Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado.");
        }
        
        String usernameLogado = authentication.getName();
        boolean removido = midiaService.remover(id, usernameLogado); // Ou chamando o repository direto
        
        if (removido) {
            return ResponseEntity.noContent().build(); // Status 204 (Deletado com sucesso)
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Mídia não encontrada ou você não tem permissão para deletá-la.");
        }
}
    /**
     * Método utilitário para converter MultipartFile em File comum do Java.
     * Isso permite extrair o '.getAbsolutePath()' que seu repositório/S3Service usa.
     */
    private File convertMultipartToFile(MultipartFile file) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }
}