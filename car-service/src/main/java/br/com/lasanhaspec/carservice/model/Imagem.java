package br.com.lasanhaspec.carservice.model;

import java.time.LocalDateTime;

public class Imagem extends Midia {

    public Imagem() {
        super();
    }

    public Imagem(Long id, String nome, String caminhoArquivo, LocalDateTime dataCadastro) {
    super(); // Inicializa a estrutura da classe pai
    this.setId(id);
    this.setNome(nome);
    this.setCaminhoArquivo(caminhoArquivo);
    this.setDataCadastro(dataCadastro);
}

    @Override
    public String toString() {
        return "Imagem{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", caminhoArquivo='" + getCaminhoArquivo() + '\'' +
                ", dataCadastro=" + getDataCadastro() +
                '}';
    }
}