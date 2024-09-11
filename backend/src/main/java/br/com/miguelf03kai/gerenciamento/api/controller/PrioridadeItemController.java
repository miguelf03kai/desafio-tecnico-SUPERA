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

import br.com.miguelf03kai.gerenciamento.domain.model.PrioridadeItem;
import br.com.miguelf03kai.gerenciamento.domain.services.PrioridadeItemService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/prioridadeitem")
public class PrioridadeItemController {

	@Autowired
	private PrioridadeItemService prioridadeItemService;

	@GetMapping
	public List<PrioridadeItem> listarPrioridades(Pageable pageable) {
		return prioridadeItemService.listarPrioridades(pageable).getContent();
	}

	@GetMapping("/{id}")
    public ResponseEntity<PrioridadeItem> buscarPrioridadesPorId(@PathVariable Long id) {
        Optional<PrioridadeItem> prioridade = prioridadeItemService.buscarPrioridadePorId(id);
        return prioridade.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PrioridadeItem> criarPrioridades(@RequestBody PrioridadeItem prioridade) {
        PrioridadeItem novaPrioridade = prioridadeItemService.criarPrioridade(prioridade);
        return ResponseEntity.ok(novaPrioridade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrioridadeItem> atualizarPrioridade(@PathVariable Long id, @RequestBody PrioridadeItem prioridadeAtualizado) {
        try {
            PrioridadeItem prioridade = prioridadeItemService.atualizarPrioridade(id, prioridadeAtualizado);
            return ResponseEntity.ok(prioridade);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPrioridade(@PathVariable Long id) {
        prioridadeItemService.deletarPrioridade(id);
        return ResponseEntity.noContent().build();
    }
}
