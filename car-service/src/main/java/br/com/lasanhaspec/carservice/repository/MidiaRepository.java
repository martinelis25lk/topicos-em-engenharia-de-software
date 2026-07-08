package br.com.lasanhaspec.carservice.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.lasanhaspec.carservice.model.Midia;
@Repository
public interface MidiaRepository  {

    Midia salvar(Midia midia);

    boolean atualizar(Midia midia);

    public boolean remover(Long id, String username);

    Midia buscarPorId(long id);

    List<Midia> listar();

    List<Midia> pesquisar(String termo);

    List<Midia> listarPorUsername(String username);
}