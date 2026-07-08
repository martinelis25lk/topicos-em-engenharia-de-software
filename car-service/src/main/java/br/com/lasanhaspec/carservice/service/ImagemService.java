package br.com.lasanhaspec.carservice.service;
import java.util.List;
import org.springframework.stereotype.Service;
import br.com.lasanhaspec.carservice.model.Imagem;
import br.com.lasanhaspec.carservice.model.Midia;
import br.com.lasanhaspec.carservice.infrastructure.storage.*;

@Service
public class ImagemService {

    private final MidiaService midiaService;
    private final S3StorageService s3StorageService; // <-- ESSA LINHA CONSERTA OS ERROS 15 e 27

    public ImagemService(MidiaService midiaService, S3StorageService s3StorageService) {
        this.midiaService = midiaService;
        this.s3StorageService = s3StorageService;
    }

    public Imagem cadastrar(Imagem imagem) {
            String caminhoLocal = imagem.getCaminhoArquivo(); 

            System.out.println("Enviando arquivo para o S3 de forma segura...");
            // O método novo se chama .upload() e precisa apenas do caminho local
            String keyS3 = s3StorageService.upload(caminhoLocal);
            
            // Se quiser salvar a URL completa no banco de dados:
            String urlCompletaS3 = "https://lasanhaspec-vehicle-images-dev.s3.amazonaws.com/" + keyS3; // mude SEU_BUCKET para o seu bucket real
            imagem.setCaminhoArquivo(urlCompletaS3);

            Midia midiaSalva = midiaService.cadastrar(imagem);
            return (Imagem) midiaSalva;
        }
        public boolean atualizar(Imagem imagem) {
            return midiaService.atualizar(imagem);
        }

    public boolean remover(long id, String username) {
        return midiaService.remover(id, username);
    }

    public Imagem buscarPorId(long id, String username) {
        return (Imagem) midiaService.buscarPorId(id, username);
    }

    public List<Imagem> listar() {
        return midiaService.listar().stream()
                .map(m -> (Imagem) m)
                .toList();
    }

    public List<Imagem> pesquisar(String termo) {
        return midiaService.pesquisar(termo).stream()
                .map(m -> (Imagem) m)
                .toList();
    }
}