package br.propjeto_tech4.tech4.controller;

import br.propjeto_tech4.tech4.dto.FormaPgtoDTO;
import br.propjeto_tech4.tech4.modal.FormaPgto;
import br.propjeto_tech4.tech4.repository.FormaPgtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formas-pgto")
public class FormaPgtoController {

    @Autowired
    private FormaPgtoRepository formaPgtoRepository;

    @GetMapping
    public ResponseEntity<List<FormaPgto>> findAll() {
        List<FormaPgto> formasPgto = formaPgtoRepository.findAll();
        return ResponseEntity.ok(formasPgto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPgto> findById(@PathVariable Integer id) {
        return formaPgtoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Forma de pagamento não encontrada com ID " + id));
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody FormaPgtoDTO dto) {
        if (dto.descricao() == null || dto.descricao().trim().isEmpty()) {
            return ResponseEntity.status(400).body("A descrição da forma de pagamento é obrigatória.");
        }

        FormaPgto formaPgto = new FormaPgto(dto.descricao());
        formaPgtoRepository.save(formaPgto);

        return ResponseEntity.status(201).body(formaPgto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormaPgto> update(@PathVariable Integer id, @RequestBody FormaPgtoDTO dto) {
        FormaPgto formaPgto = formaPgtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Forma de pagamento não encontrada com ID " + id));

        if (dto.descricao() != null && !dto.descricao().trim().isEmpty()) {
            formaPgto.setDescricao(dto.descricao());
        }

        formaPgtoRepository.save(formaPgto);

        return ResponseEntity.ok(formaPgto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        FormaPgto formaPgto = formaPgtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Forma de pagamento não encontrada com ID " + id));

        formaPgtoRepository.delete(formaPgto);

        return ResponseEntity.noContent().build();
    }
}