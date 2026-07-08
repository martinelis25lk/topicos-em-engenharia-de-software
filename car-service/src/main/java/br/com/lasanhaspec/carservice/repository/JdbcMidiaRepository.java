package br.com.lasanhaspec.carservice.repository;

import br.com.lasanhaspec.carservice.model.Midia;
import br.com.lasanhaspec.carservice.model.Imagem;
import br.com.lasanhaspec.carservice.model.Audio;
import br.com.lasanhaspec.carservice.model.Video;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMidiaRepository implements MidiaRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcMidiaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
public Midia salvar(Midia midia) {
    // 💡 Adicionada a coluna 'tipo' no INSERT para salvar se é IMAGEM, AUDIO ou VIDEO
    String sql = "INSERT INTO midia (nome, caminho_arquivo, data_cadastro, username, tipo) VALUES (?, ?, ?, ?, ?)";
    
    KeyHolder keyHolder = new GeneratedKeyHolder();

    int linhasAfetadas = jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, midia.getNome());
        ps.setString(2, midia.getCaminhoArquivo());
        ps.setString(3, midia.getDataCadastro() != null ? midia.getDataCadastro().toString() : LocalDateTime.now().toString());
        ps.setString(4, midia.getUsername());
        
        // 💡 Usa o método utilitário que você já tem na classe para descobrir a String do tipo
        ps.setString(5, obterTipo(midia)); 
        
        return ps;
    }, keyHolder);

    if (linhasAfetadas == 0) {
        throw new RuntimeException("Falha ao salvar a mídia, nenhuma linha alterada.");
    }

    if (keyHolder.getKey() != null) {
        midia.setId(keyHolder.getKey().longValue());
    }

    return midia;
}

    @Override
    public boolean atualizar(Midia midia) {
        String sql = "UPDATE midia SET nome = ?, caminho_arquivo = ?, data_cadastro = ? WHERE id = ?";
        
        int rows = jdbcTemplate.update(sql, 
                midia.getNome(), 
                midia.getCaminhoArquivo(), 
                midia.getDataCadastro() != null ? midia.getDataCadastro().toString() : null, 
                midia.getId()
        );
        
        return rows > 0;
    }

    @Override
    public boolean remover(Long id, String username) {
        // 💡 Agora a query checa o ID e garante que a mídia pertence ao usuário logado
        String sql = "DELETE FROM midia WHERE id = ? AND username = ?";
        
        int rows = jdbcTemplate.update(sql, id, username);
        
        // Retorna true se deletou com sucesso, ou false se não encontrou/não tinha permissão
        return rows > 0; 
    }
    @Override
    public Midia buscarPorId(long id) {
        String sql = "SELECT * FROM midia WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> criarMidia(rs), id);
        } catch (EmptyResultDataAccessException e) {
            return null; // Mantém o comportamento original caso não encontre o registro
        }
    }

    @Override
    public List<Midia> listar() {
        String sql = "SELECT * FROM midia ORDER BY id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> criarMidia(rs));
    }

    @Override
    public List<Midia> pesquisar(String termo) {
        String sql = "SELECT * FROM midia WHERE nome LIKE ? ORDER BY nome";
        return jdbcTemplate.query(sql, (rs, rowNum) -> criarMidia(rs), "%" + termo + "%");
    }

    private String obterTipo(Midia midia) {
        if (midia instanceof Imagem) return "IMAGEM";
        if (midia instanceof Audio) return "AUDIO";
        if (midia instanceof Video) return "VIDEO";
        throw new IllegalArgumentException("Tipo de mídia desconhecido.");
    }

   private Midia criarMidia(ResultSet rs) throws SQLException {
        // 💡 Alterado para rs.getLong() para bater com o novo tipo Long da classe pai
        Long id = rs.getLong("id"); 
        String nome = rs.getString("nome");
        String caminhoArquivo = rs.getString("caminho_arquivo");
        
         // 💡 Substitua as linhas antigas por esta leitura direta do JDBC:
        java.sql.Timestamp timestamp = rs.getTimestamp("data_cadastro");
        LocalDateTime dataCadastro = timestamp != null ? timestamp.toLocalDateTime() : null;
      
        String tipo = rs.getString("tipo");
        Midia midia;
        switch (tipo) {
            case "IMAGEM":
                midia = new Imagem(id, nome, caminhoArquivo, dataCadastro);
                break;
            case "AUDIO":
                midia = new Audio(id, nome, caminhoArquivo, dataCadastro);
                break;
            case "VIDEO":
                midia = new Video(id, nome, caminhoArquivo, dataCadastro);
                break;
            default:
                throw new IllegalArgumentException("Tipo de mídia inválido: " + tipo);
        }
        midia.setTipo(tipo.toUpperCase()); 
        midia.setUsername(rs.getString("username"));
        
        return midia;
    }

    @Override
    public List<Midia> listarPorUsername(String username) {
        String sql = "SELECT * FROM midia WHERE username = ? ORDER BY id";
        
        // O RowMapper (rs, rowNum) -> criarMidia(rs) vai instanciar a classe filha correta automaticamente!
        return jdbcTemplate.query(sql, (rs, rowNum) -> criarMidia(rs), username);
    }
}