package br.com.miguelf03kai.gerenciamento.domain.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.miguelf03kai.gerenciamento.domain.model.Lista;
import br.com.miguelf03kai.gerenciamento.domain.repository.ListaRepository;

@Service
public class ListaService {

	@Autowired
	private ListaRepository listaRepository;
	
	public Page<Lista> listarListas(Pageable pageable){
		return listaRepository.findAll(pageable);
	}

    public Optional<Lista> buscarListaPorId(Long id) {
        return listaRepository.findById(id);
    }

	public Lista criarLista(Lista lista) {
        return listaRepository.save(lista);
    }

    public void deletarLista(Long id) {
        listaRepository.deleteById(id);
    }

	public Lista atualizarLista(Long id, Lista listaAtualizado) {
        Optional<Lista> listaExistenteOptional = listaRepository.findById(id);

        if (listaExistenteOptional.isPresent()) {
            Lista listaExistente = listaExistenteOptional.get();
            listaExistente.setDescricao(listaAtualizado.getDescricao());

            return listaRepository.save(listaExistente);
        } else {
            throw new IllegalArgumentException("Item não encontrado com o ID: " + id);
        }
    }
}
