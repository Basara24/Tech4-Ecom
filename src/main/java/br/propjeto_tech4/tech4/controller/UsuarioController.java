package br.propjeto_tech4.tech4.controller;

import br.propjeto_tech4.tech4.dto.UsuarioDTO;
import br.propjeto_tech4.tech4.modal.Usuario;
import br.propjeto_tech4.tech4.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Integer id) {
        return usuarioRepository.findById(id)
                .map(usuario -> ResponseEntity.ok(usuario))
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado com ID " + id));
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody UsuarioDTO dto) {
        // Validação do preço
        if ((dto.email() == null  || dto.email().trim().isEmpty())) {
            return ResponseEntity.status(400).body("O email deve ser digitado.");
        }

        // Validação da descrição
        if (dto.nome() == null || dto.nome().trim().isEmpty()) {
            return ResponseEntity.status(400).body("O nome não pode estar vazia.");
        }

        // Criação do objeto Item
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());

        // Salvando no banco de dados
        this.usuarioRepository.save(usuario);

        // Retornando o objeto salvo
        return ResponseEntity.status(201).body(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Integer id, @RequestBody UsuarioDTO usuarioDTO) {
        // Busca pelo usuário
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado com ID " + id));

        // Atualiza os dados
        if (usuarioDTO.nome() != null && !usuarioDTO.nome().trim().isEmpty()) {
            usuario.setNome(usuarioDTO.nome());
        }
        if (usuarioDTO.email() != null && !usuarioDTO.email().trim().isEmpty()) {
            usuario.setEmail(usuarioDTO.email());
        }

        // Salvar no banco de dados
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado com ID " + id));

        usuarioRepository.delete(usuario);

        return ResponseEntity.noContent().build();
    }
}