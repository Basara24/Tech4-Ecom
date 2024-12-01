package br.propjeto_tech4.tech4.controller;

import br.propjeto_tech4.tech4.dto.PagamentoDTO;
import br.propjeto_tech4.tech4.modal.Pagamento;
import br.propjeto_tech4.tech4.modal.Pedido;
import br.propjeto_tech4.tech4.modal.FormaPgto;
import br.propjeto_tech4.tech4.repository.PagamentoRepository;
import br.propjeto_tech4.tech4.repository.PedidoRepository;
import br.propjeto_tech4.tech4.repository.FormaPgtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private FormaPgtoRepository formaPgtoRepository;

    @GetMapping
    public ResponseEntity<List<Pagamento>> findAll() {
        List<Pagamento> pagamentos = pagamentoRepository.findAll();
        return ResponseEntity.ok(pagamentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> findById(@PathVariable Integer id) {
        return pagamentoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado com ID " + id));
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody PagamentoDTO dto) {
        // Validação do valor
        if (dto.valor() == null || dto.valor().compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.status(400).body("O valor do pagamento deve ser maior que zero.");
        }

        // Busca do pedido
        Pedido pedido = pedidoRepository.findById(dto.pedidoId())
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID " + dto.pedidoId()));

        // Busca da forma de pagamento
        FormaPgto formaPgto = formaPgtoRepository.findById(dto.formaPgtoId())
                .orElseThrow(() -> new RuntimeException("Forma de pagamento não encontrada com ID " + dto.formaPgtoId()));

        // Criação do objeto Pagamento
        Pagamento pagamento = new Pagamento(pedido, formaPgto, dto.valor());
        pagamentoRepository.save(pagamento);

        return ResponseEntity.status(201).body(pagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id, @RequestBody PagamentoDTO dto) {
        // Busca pelo pagamento
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado com ID " + id));

        // Atualizações condicionais
        if (dto.valor() != null && dto.valor().compareTo(BigDecimal.ZERO) > 0) {
            pagamento.setValor(dto.valor());
        }

        if (dto.pedidoId() != null) {
            Pedido pedido = pedidoRepository.findById(dto.pedidoId())
                    .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID " + dto.pedidoId()));
            pagamento.setPedido(pedido);
        }

        if (dto.formaPgtoId() != null) {
            FormaPgto formaPgto = formaPgtoRepository.findById(dto.formaPgtoId())
                    .orElseThrow(() -> new RuntimeException("Forma de pagamento não encontrada com ID " + dto.formaPgtoId()));
            pagamento.setFormaPgto(formaPgto);
        }

        pagamentoRepository.save(pagamento);

        return ResponseEntity.ok(pagamento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado com ID " + id));

        pagamentoRepository.delete(pagamento);

        return ResponseEntity.noContent().build();
    }
}