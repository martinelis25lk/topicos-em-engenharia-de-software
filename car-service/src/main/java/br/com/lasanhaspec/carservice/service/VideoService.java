package br.com.lasanhaspec.carservice.service;

import br.com.lasanhaspec.carservice.model.Midia;
import br.com.lasanhaspec.carservice.model.Video;
import br.com.lasanhaspec.carservice.infrastructure.storage.*;


import java.util.List;

import org.springframework.stereotype.Service;


@Service
public class VideoService {

    private final MidiaService midiaService;
    private final S3StorageService s3StorageService; // <-- ADICIONADO

    // Ajustado o construtor para receber o S3 também
    public VideoService(MidiaService midiaService, S3StorageService s3StorageService) { 
        this.midiaService = midiaService;
        this.s3StorageService = s3StorageService;
    }

    public Video cadastrar(Video video) {
        String caminhoLocal = video.getCaminhoArquivo();

        System.out.println("Enviando vídeo para o S3 de forma segura...");
        // 1. Faz o upload real do vídeo para a AWS
        String keyS3 = s3StorageService.upload(caminhoLocal);
        
        // 2. Monta a URL da AWS (Lembre de ajustar para o nome do seu bucket e região)
        String urlCompletaS3 = "https://lasanhaspec-vehicle-images-dev.s3.us-east-2.amazonaws.com/" + keyS3;
        video.setCaminhoArquivo(urlCompletaS3);

        // 3. Salva no banco de dados com a nova URL do S3
        Midia midiaSalva = midiaService.cadastrar(video);
        return (Video) midiaSalva;
    }

    public boolean atualizar(Video video) {
        return midiaService.atualizar(video);
    }

    public boolean remover(long id, String username) {
        return midiaService.remover(id, username);
    }

    public Video buscarPorId(long id, String username) {
        return (Video) midiaService.buscarPorId(id, username);
    }

    public List<Video> listar() {
        return midiaService.listar().stream()
                .map(m -> (Video) m)
                .toList();
    }

    public List<Video> pesquisar(String termo) {
        return midiaService.pesquisar(termo).stream()
                .map(m -> (Video) m)
                .toList();
    }
}