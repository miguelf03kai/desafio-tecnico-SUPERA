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

import br.com.miguelf03kai.gerenciamento.domain.model.Lista;
import br.com.miguelf03kai.gerenciamento.domain.services.ListaService;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/lista")
public class ListaController {

	@Autowired
	private ListaService listaService;

	@GetMapping
	public List<Lista> listarListas(Pageable pageable) {
		return listaService.listarListas(pageable).getContent();
	}

    /*@GetMapping("/{id}")
    public ResponseEntity<Lista> buscarListaComItens(@PathVariable Long id) {
        Optional<Lista> listaOptional = listaService.buscarListaPorId(id);
        if (listaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Lista lista = listaOptional.get();
        return ResponseEntity.ok(lista);
    }*/

	@GetMapping("/{id}")
    public ResponseEntity<Lista> buscarListaPorId(@PathVariable Long id) {
        Optional<Lista> lista = listaService.buscarListaPorId(id);
        return lista.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Lista> criarLista(@RequestBody Lista lista) {
        Lista novaLista = listaService.criarLista(lista);
        return ResponseEntity.ok(novaLista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lista> atualizarLista(@PathVariable Long id, @RequestBody Lista listaAtualizado) {
        try {
            Lista lista = listaService.atualizarLista(id, listaAtualizado);
            return ResponseEntity.ok(lista);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLista(@PathVariable Long id) {
        listaService.deletarLista(id);
        return ResponseEntity.noContent().build();
    }
}
