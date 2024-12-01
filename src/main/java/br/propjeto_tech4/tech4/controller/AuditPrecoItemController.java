package br.propjeto_tech4.tech4.controller;

import br.propjeto_tech4.tech4.dto.AuditPrecoItemDTO;
import br.propjeto_tech4.tech4.modal.AuditPrecoItem;
import br.propjeto_tech4.tech4.modal.Item;
import br.propjeto_tech4.tech4.repository.AuditPrecoItemRepository;
import br.propjeto_tech4.tech4.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/audit-preco-item")
public class AuditPrecoItemController {

    @Autowired
    private AuditPrecoItemRepository auditPrecoItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public ResponseEntity<List<AuditPrecoItem>> findAll() {
        List<AuditPrecoItem> audits = auditPrecoItemRepository.findAll();
        return ResponseEntity.ok(audits);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody AuditPrecoItemDTO dto) {
        // Validação de preços
        if (dto.precoAntigo() == null || dto.precoNovo() == null || dto.precoNovo().compareTo(dto.precoAntigo()) == 0) {
            return ResponseEntity.status(400).body("Preços inválidos ou iguais.");
        }

        // Busca pelo item
        Item item = itemRepository.findById(dto.itemId())
                .orElseThrow(() -> new RuntimeException("Item não encontrado com ID " + dto.itemId()));

        // Criação do objeto AuditPrecoItem
        AuditPrecoItem audit = new AuditPrecoItem(item, dto.precoAntigo(), dto.precoNovo(), LocalDateTime.now());
        auditPrecoItemRepository.save(audit);

        return ResponseEntity.status(201).body(audit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        AuditPrecoItem audit = auditPrecoItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Audit não encontrado com ID " + id));

        auditPrecoItemRepository.delete(audit);

        return ResponseEntity.noContent().build();
    }
}