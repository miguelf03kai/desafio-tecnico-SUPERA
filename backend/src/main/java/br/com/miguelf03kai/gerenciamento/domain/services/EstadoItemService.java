package br.com.miguelf03kai.gerenciamento.domain.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.miguelf03kai.gerenciamento.domain.model.EstadoItem;
import br.com.miguelf03kai.gerenciamento.domain.repository.EstadoRepository;

@Service
public class EstadoItemService {

    @Autowired
    private EstadoRepository estadoRepository;
	
	public Page<EstadoItem> listarEstados(Pageable pageable){
		return estadoRepository.findAll(pageable);
	}

    public Optional<EstadoItem> buscarEstadoPorId(Long id) {
        return estadoRepository.findById(id);
    }

	public EstadoItem criarEstado(EstadoItem estadoItem) {
        return estadoRepository.save(estadoItem);
    }

    public void deletarEstado(Long id) {
        estadoRepository.deleteById(id);
    }

	public EstadoItem atualizarEstado(Long id, EstadoItem estadoAtualizado) {
        Optional<EstadoItem> estadoExistente = estadoRepository.findById(id);

        if (estadoExistente.isPresent()) {
            EstadoItem estadoParaAtualizar = new EstadoItem(
                id,
                estadoAtualizado.getDescricao()
            );
            return estadoRepository.save(estadoParaAtualizar);
        } else {
            throw new IllegalArgumentException("Estado de Item não encontrado com o ID: " + id);
        }
    }
}
