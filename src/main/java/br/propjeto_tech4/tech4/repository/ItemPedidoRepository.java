package br.propjeto_tech4.tech4.repository;

import br.propjeto_tech4.tech4.modal.ItemPedido;
import br.propjeto_tech4.tech4.modal.ItemPedidoId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoId> {
}
