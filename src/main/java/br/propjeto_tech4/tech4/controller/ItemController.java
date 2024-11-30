package br.propjeto_tech4.tech4.controller;

import br.propjeto_tech4.tech4.dto.ItemRequestDTO;
import br.propjeto_tech4.tech4.modal.Item;
import br.propjeto_tech4.tech4.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemRepository repository;

    @GetMapping
    public List<Item> findAll() {
        return repository.findAll(); // Usa o repositório para buscar os itens no banco
    }

    @GetMapping("/{id}")
    public Item findById(@PathVariable Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Smartphone nao encontrado"));
    }

    @PostMapping
    public Item save(@RequestBody ItemRequestDTO dto){
        Item item = new Item();
        item.setDescricao(dto.descricao());
        item.setPreco(dto.preco());

       return this.repository.save(item);
    }

}