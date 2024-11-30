package br.propjeto_tech4.tech4.repository;

import br.propjeto_tech4.tech4.modal.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item , Integer> {


}
