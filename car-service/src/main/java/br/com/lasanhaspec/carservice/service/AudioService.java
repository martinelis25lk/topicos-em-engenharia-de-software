package br.com.lasanhaspec.carservice.service;


import java.util.List;

import org.springframework.stereotype.Service;

import br.com.lasanhaspec.carservice.model.*;
import br.com.lasanhaspec.carservice.infrastructure.storage.*;
@Service
public class AudioService {

    private final MidiaService midiaService;
    private final S3StorageService s3StorageService;

    public AudioService(MidiaService midiaService, S3StorageService s3StorageService) {
        this.midiaService = midiaService;
        this.s3StorageService = s3StorageService;
    }

    public Audio cadastrar(Audio audio) {
        String caminhoLocal = audio.getCaminhoArquivo();

        System.out.println("Enviando áudio para o S3 de forma segura...");
        // 1. Faz o upload real do áudio para a AWS
        String keyS3 = s3StorageService.upload(caminhoLocal);
        
        // 2. Monta a URL da AWS com o seu bucket
        String urlCompletaS3 = "https://lasanhaspec-vehicle-images-dev.s3.us-east-2.amazonaws.com/" + keyS3;
        audio.setCaminhoArquivo(urlCompletaS3);

        // 3. Salva no banco de dados SQLite local
        Midia midiaSalva = midiaService.cadastrar(audio);
        return (Audio) midiaSalva;
    }

    public boolean atualizar(Audio audio) {
        return midiaService.atualizar(audio);
    }

    public boolean remover(long id, String username) {
        return midiaService.remover(id, username);
    }

    public Audio buscarPorId(long id, String username) {
        return (Audio) midiaService.buscarPorId(id, username);
    }

    public List<Audio> listar() {
        return midiaService.listar().stream()
                .map(m -> (Audio) m)
                .toList();
    }
}