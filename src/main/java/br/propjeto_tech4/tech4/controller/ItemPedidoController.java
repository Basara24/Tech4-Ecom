package br.propjeto_tech4.tech4.controller;

import br.propjeto_tech4.tech4.dto.ItemPedidoDTO;
import br.propjeto_tech4.tech4.modal.ItemPedido;
import br.propjeto_tech4.tech4.modal.Item;
import br.propjeto_tech4.tech4.modal.ItemPedidoId;
import br.propjeto_tech4.tech4.modal.Pedido;
import br.propjeto_tech4.tech4.repository.ItemPedidoRepository;
import br.propjeto_tech4.tech4.repository.ItemRepository;
import br.propjeto_tech4.tech4.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item-pedidos")
public class ItemPedidoController {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public ResponseEntity<List<ItemPedido>> findAll() {
        List<ItemPedido> itemPedidos = itemPedidoRepository.findAll();
        return ResponseEntity.ok(itemPedidos);
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody ItemPedidoDTO dto) {
        // Validações
        if (dto.quantidade() == null || dto.quantidade() <= 0) {
            return ResponseEntity.status(400).body("A quantidade deve ser maior que zero.");
        }
        if (dto.preco() == null || dto.preco().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            return ResponseEntity.status(400).body("O preço deve ser maior que zero.");
        }

        // Busca pelo pedido
        Pedido pedido = pedidoRepository.findById(dto.pedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID " + dto.pedidoId()));

        // Busca pelo item
        Item item = itemRepository.findById(dto.itemId())
                .orElseThrow(() -> new RuntimeException("Item não encontrado com ID " + dto.itemId()));

        // Criação do objeto ItemPedido
        ItemPedido itemPedido = new ItemPedido(pedido, item, dto.quantidade(), dto.preco());
        itemPedidoRepository.save(itemPedido);

        return ResponseEntity.status(201).body(itemPedido);
    }

    @DeleteMapping("/{pedidoId}/{itemId}")
    public ResponseEntity<Void> delete(@PathVariable Integer pedidoId, @PathVariable Integer itemId) {
        ItemPedidoId id = new ItemPedidoId(pedidoId, itemId);
        ItemPedido itemPedido = itemPedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ItemPedido não encontrado com Pedido ID " + pedidoId + " e Item ID " + itemId));

        itemPedidoRepository.delete(itemPedido);

        return ResponseEntity.noContent().build();
    }
}
