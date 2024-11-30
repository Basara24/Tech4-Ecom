package br.propjeto_tech4.tech4.controller;

import br.propjeto_tech4.tech4.modal.Item;
import br.propjeto_tech4.tech4.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}