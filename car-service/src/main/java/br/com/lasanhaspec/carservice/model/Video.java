
package br.com.lasanhaspec.carservice.model;
import java.time.LocalDateTime;

public class Video extends Midia {

    public Video() {
    }

    public Video(Long id, String nome, String caminhoArquivo, LocalDateTime dataCadastro) {
    super(); // Inicializa a estrutura da classe pai
    this.setId(id);
    this.setNome(nome);
    this.setCaminhoArquivo(caminhoArquivo);
    this.setDataCadastro(dataCadastro);
}

    @Override
    public String toString() {
        return "Video{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", caminho='" + getCaminhoArquivo() + '\'' +
                ", dataCadastro=" + getDataCadastro() +
                '}';
    }
}