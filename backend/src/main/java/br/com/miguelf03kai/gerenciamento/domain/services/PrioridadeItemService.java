package br.com.miguelf03kai.gerenciamento.domain.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.miguelf03kai.gerenciamento.domain.model.PrioridadeItem;
import br.com.miguelf03kai.gerenciamento.domain.repository.PrioridadeRepository;

@Service
public class PrioridadeItemService {

    @Autowired
    private PrioridadeRepository prioridadeRepository;
	
	public Page<PrioridadeItem> listarPrioridades(Pageable pageable){
		return prioridadeRepository.findAll(pageable);
	}

    public Optional<PrioridadeItem> buscarPrioridadePorId(Long id) {
        return prioridadeRepository.findById(id);
    }

	public PrioridadeItem criarPrioridade(PrioridadeItem prioridadeItem) {
        return prioridadeRepository.save(prioridadeItem);
    }

    public void deletarPrioridade(Long id) {
        prioridadeRepository.deleteById(id);
    }

	public PrioridadeItem atualizarPrioridade(Long id, PrioridadeItem prioridadeAtualizado) {
        Optional<PrioridadeItem> prioridadeExistente = prioridadeRepository.findById(id);

        if (prioridadeExistente.isPresent()) {
            PrioridadeItem prioridadeParaAtualizar = new PrioridadeItem(
                id,
                prioridadeAtualizado.getDescricao(),
                prioridadeAtualizado.getCor()
            );
            return prioridadeRepository.save(prioridadeParaAtualizar);
        } else {
            throw new IllegalArgumentException("Prioridade de Item não encontrado com o ID: " + id);
        }
    }
}
