package br.propjeto_tech4.tech4.controller;

import br.propjeto_tech4.tech4.dto.ItemRequestDTO;
import br.propjeto_tech4.tech4.modal.Item;
import br.propjeto_tech4.tech4.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemRepository repository;

    @GetMapping
    public ResponseEntity <List<Item>> findAll() {
        List<Item> items = repository.findAll(); // Usa o repositório para buscar os itens no banco
        return ResponseEntity.ok(items);

    }

    @GetMapping("/{id}")
    public Item findById(@PathVariable Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Smartphone nao encontrado"));
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody ItemRequestDTO dto) {
        // Validação do preço
        if ((dto.preco() == null)) {
            return ResponseEntity.status(400).body("O preço deve ser maior que zero.");
        }

        // Validação da descrição
        if (dto.descricao() == null || dto.descricao().trim().isEmpty()) {
            return ResponseEntity.status(400).body("A descrição não pode estar vazia.");
        }

        // Criação do objeto Item
        Item item = new Item();
        item.setDescricao(dto.descricao());
        item.setPreco(dto.preco());

        // Salvando no banco de dados
        this.repository.save(item);

        // Retornando o objeto salvo
        return ResponseEntity.status(201).body(item);
    }
}