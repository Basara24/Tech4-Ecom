package br.propjeto_tech4.tech4.repository;

import br.propjeto_tech4.tech4.modal.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
}