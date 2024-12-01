package br.propjeto_tech4.tech4.dto;

import java.math.BigDecimal;

public record PagamentoDTO(Integer pedidoId, Integer formaPgtoId, BigDecimal valor) {
}