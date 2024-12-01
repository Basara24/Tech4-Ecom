package br.propjeto_tech4.tech4.repository;

import br.propjeto_tech4.tech4.modal.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}