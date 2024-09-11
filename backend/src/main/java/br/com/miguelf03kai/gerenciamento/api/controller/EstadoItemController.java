package br.com.miguelf03kai.gerenciamento.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.miguelf03kai.gerenciamento.domain.model.EstadoItem;
import br.com.miguelf03kai.gerenciamento.domain.services.EstadoItemService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/estadoitem")
public class EstadoItemController {

	@Autowired
	private EstadoItemService estadosItemService;

	@GetMapping
	public List<EstadoItem> listarEstados(Pageable pageable) {
		return estadosItemService.listarEstados(pageable).getContent();
	}

	@GetMapping("/{id}")
    public ResponseEntity<EstadoItem> buscarEstadoItemPorId(@PathVariable Long id) {
        Optional<EstadoItem> estadoItem = estadosItemService.buscarEstadoPorId(id);
        return estadoItem.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EstadoItem> criarItem(@RequestBody EstadoItem estadoItem) {
        EstadoItem novaEstado = estadosItemService.criarEstado(estadoItem);
        return ResponseEntity.ok(novaEstado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoItem> atualizarPrioridade(@PathVariable Long id, @RequestBody EstadoItem estadoItemAtualizado) {
        try {
            EstadoItem estadoItem = estadosItemService.atualizarEstado(id, estadoItemAtualizado);
            return ResponseEntity.ok(estadoItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPrioridade(@PathVariable Long id) {
        estadosItemService.deletarEstado(id);
        return ResponseEntity.noContent().build();
    }
}
