package br.propjeto_tech4.tech4.controller;

import br.propjeto_tech4.tech4.dto.PedidoDTO;
import br.propjeto_tech4.tech4.modal.Pedido;
import br.propjeto_tech4.tech4.modal.Usuario;
import br.propjeto_tech4.tech4.repository.PedidoRepository;
import br.propjeto_tech4.tech4.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<Pedido>> findAll() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> findById(@PathVariable Integer id) {
        return pedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID " + id));
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody PedidoDTO dto) {
        // Validar se o usuário existe
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID " + dto.usuarioId()));

        // Criar o objeto Pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setDataPedido(dto.dataPedido());
        pedido.setValorTotal(dto.valorTotal());

        // Salvar no banco de dados
        pedidoRepository.save(pedido);

        return ResponseEntity.status(201).body(pedido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> update(@PathVariable Integer id, @RequestBody PedidoDTO dto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID " + id));

        // Atualizar os dados do pedido
        if (dto.usuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID " + dto.usuarioId()));
            pedido.setUsuario(usuario);
        }
        if (dto.dataPedido() != null) {
            pedido.setDataPedido(dto.dataPedido());
        }
        if (dto.valorTotal() != null) {
            pedido.setValorTotal(dto.valorTotal());
        }

        // Salvar alterações no banco
        pedidoRepository.save(pedido);

        return ResponseEntity.ok(pedido);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID " + id));

        pedidoRepository.delete(pedido);

        return ResponseEntity.noContent().build();
    }
}