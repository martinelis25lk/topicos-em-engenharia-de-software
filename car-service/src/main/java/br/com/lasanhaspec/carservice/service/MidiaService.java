package br.com.lasanhaspec.carservice.service;

import br.com.lasanhaspec.carservice.model.*;
import br.com.lasanhaspec.carservice.repository.MidiaRepository;
import br.com.lasanhaspec.carservice.infrastructure.storage.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MidiaService {

    private final MidiaRepository repository;
    private final S3StorageService s3StorageService; // <-- ADICIONADO AQUI

    // Construtor atualizado para inicializar o s3StorageService
    public MidiaService(MidiaRepository repository) {
        this.repository = repository;
        this.s3StorageService = new S3StorageService(); // <-- ADICIONADO AQUI
    }

    public Midia cadastrar(Midia midia) {

        validarMidia(midia);

        if (midia.getDataCadastro() == null) {
            midia.setDataCadastro(LocalDateTime.now());
        }

        return repository.salvar(midia);
    }

    public boolean atualizar(Midia midia) {

        validarMidia(midia);

        return repository.atualizar(midia);
    }
    public boolean remover(Long id, String username) {
        // 1. Tenta buscar a mídia pelo repositório
        Midia midia = repository.buscarPorId(id);
        String urlS3 = null;

        if (midia != null) {
            urlS3 = midia.getCaminhoArquivo();
        }

        // 2. Se achou a URL (seja pelo objeto ou direto), tenta detonar no S3
        if (urlS3 != null && urlS3.contains(".amazonaws.com/")) {
            try {
                String keyS3 = urlS3.substring(urlS3.lastIndexOf("/") + 1);
                System.out.println("Encontrado no S3! Removendo arquivo: " + keyS3);
                s3StorageService.delete(keyS3);
            } catch (Exception e) {
                System.err.println("Aviso: Falha ao limpar o S3: " + e.getMessage());
            }
        } else {
            System.out.println("Aviso: Nenhuma URL de S3 encontrada para o ID " + id + ". Prosseguindo para limpar o banco.");
        }

        // 3. Executa a remoção no banco de dados por ID bruto
        boolean deletadoNoBanco = repository.remover(id, username);
        
        if (!deletadoNoBanco) {
            System.out.println("Erro: O ID " + id + " realmente não existe na tabela do SQLite.");
        }

        return deletadoNoBanco;
    }




    public Midia buscarPorId(long id, String username) {

        if (id <= 0) {
            throw new IllegalArgumentException("ID inválido.");
        }

        return repository.buscarPorId(id);
    }

    public List<Midia> listar() {
        return repository.listar();
    }

    public List<Midia> pesquisar(String termo) {

        if (termo == null) {
            termo = "";
        }

        return repository.pesquisar(termo.trim());
    }

    private void validarMidia(Midia midia) {

        if (midia == null) {
            throw new IllegalArgumentException("A mídia não pode ser nula.");
        }

        if (midia.getNome() == null || midia.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da mídia é obrigatório.");
        }

        if (midia.getCaminhoArquivo() == null || midia.getCaminhoArquivo().trim().isEmpty()) {
            throw new IllegalArgumentException("O caminho do arquivo é obrigatório.");
        }
    }

            public List<Midia> listarPorUsername(String username) {
            return repository.listarPorUsername(username);
        }
}