package br.propjeto_tech4.tech4.repository;

import br.propjeto_tech4.tech4.modal.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Você pode adicionar métodos personalizados aqui, se necessário.
}
