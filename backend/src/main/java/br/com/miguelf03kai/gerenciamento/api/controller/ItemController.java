    package br.com.miguelf03kai.gerenciamento.api.controller;

    import java.util.List;
    import java.util.Optional;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Pageable;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.RequestBody;

    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import br.com.miguelf03kai.gerenciamento.domain.model.Item;
    import br.com.miguelf03kai.gerenciamento.domain.services.ItemService;


    @CrossOrigin(origins = "http://localhost:4200")
    @RestController
    @RequestMapping("/item")
    public class ItemController {

        @Autowired
        private ItemService itemService;

        @GetMapping
        public List<Item> listarItens(Pageable pageable) {
            return itemService.listarItens(pageable).getContent();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Item> buscarItemPorId(@PathVariable Long id) {
            Optional<Item> item = itemService.buscarItemPorId(id);
            return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @PostMapping
        public ResponseEntity<Item> criarItem(@RequestBody Item item) {
            Item novoItem = itemService.criarItem(item);
            return ResponseEntity.ok(novoItem);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Item> atualizarItem(@PathVariable Long id, @RequestBody Item itemAtualizado) {
            try {
                Item item = itemService.atualizarItem(id, itemAtualizado);
                return ResponseEntity.ok(item);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
            itemService.deletarItem(id);
            return ResponseEntity.noContent().build();
        }
    }
