package br.com.lasanhaspec.carservice.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
@Table(name = "midia")
public class Midia { // 💡 Removido o 'abstract' para funcionar direto como tabela

    @Id // 💡 Adicionado para resolver o erro 'has no identifier'
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT no MySQL
    private Long id; // 💡 Alterado de int para Long (padrão recomendado para chaves do JPA)
    
    private String nome;
    
    @Column(name = "caminho_arquivo") // Garante o mapeamento correto com o banco
    private String caminhoArquivo;
    
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(name = "user_id", nullable = true) // 💡 Nova coluna não obrigatória para o ID do usuário
    private Long userId;

    public String username;

    // Construtor padrão obrigatório pelo JPA
    public Midia() {
    }
    
    // Construtor prático para criação rápida antes de salvar
    public Midia(String nome, String caminhoArquivo, Long userId) {
        this.nome = nome;
        this.caminhoArquivo = caminhoArquivo;
        this.userId = userId;
        this.dataCadastro = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void getUsername(String username) {
        this.username = username;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }


    // Dentro de Midia.java, adicione:
    private String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    @Override
    public String toString() {
        return "Midia{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", caminhoArquivo='" + caminhoArquivo + '\'' +
                ", dataCadastro=" + dataCadastro +
                ", userId=" + userId +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}