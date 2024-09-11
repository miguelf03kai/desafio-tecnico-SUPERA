package br.com.miguelf03kai.gerenciamento.domain.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.miguelf03kai.gerenciamento.domain.model.EstadoItem;
import br.com.miguelf03kai.gerenciamento.domain.model.Item;
import br.com.miguelf03kai.gerenciamento.domain.model.Lista;
import br.com.miguelf03kai.gerenciamento.domain.model.PrioridadeItem;
import br.com.miguelf03kai.gerenciamento.domain.repository.EstadoRepository;
import br.com.miguelf03kai.gerenciamento.domain.repository.ItemRepository;
import br.com.miguelf03kai.gerenciamento.domain.repository.ListaRepository;
import br.com.miguelf03kai.gerenciamento.domain.repository.PrioridadeRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private ListaRepository listaRepository;

    @Autowired
    private PrioridadeRepository prioridadeRepository;
	
	public Page<Item> listarItens(Pageable pageable){
		return itemRepository.findAll(pageable);
	}

    public Optional<Item> buscarItemPorId(Long id) {
        return itemRepository.findById(id);
    }

	public Item criarItem(Item item) {
        validarReferencias(item);
        return itemRepository.save(item);
    }

    public void deletarItem(Long id) {
        itemRepository.deleteById(id);
    }

	public Item atualizarItem(Long id, Item itemAtualizado) {
        validarReferencias(itemAtualizado);
        Optional<Item> itemExistente = itemRepository.findById(id);

        if (itemExistente.isPresent()) {
            Item item = itemExistente.get();
            item.setDescricao(itemAtualizado.getDescricao());
            item.setItemPrioridadeId(itemAtualizado.getItemPrioridadeId());
            item.setItemEstadoId(itemAtualizado.getItemEstadoId());
            item.setListaId(itemAtualizado.getListaId());

            return itemRepository.save(item);
        } else {
            throw new IllegalArgumentException("Item não encontrado com o ID: " + id);
        }
    }

	private void validarReferencias(Item item) {
        Optional<EstadoItem> estado = estadoRepository.findById((long) item.getItemEstadoId());
        Optional<PrioridadeItem> prioridade = prioridadeRepository.findById((long) item.getItemPrioridadeId());
        Optional<Lista> lista = listaRepository.findById((long) item.getListaId());

        if (estado.isEmpty()) {
            throw new Error("Estado inválido para o ID: " + item.getItemEstadoId());
        }

        if (prioridade.isEmpty()) {
            throw new Error("Prioridade inválida para o ID: " + item.getItemPrioridadeId());
        }

        if (lista.isEmpty()) {
            throw new Error("Lista inválida para o ID: " + item.getListaId());
        }
    }
}
