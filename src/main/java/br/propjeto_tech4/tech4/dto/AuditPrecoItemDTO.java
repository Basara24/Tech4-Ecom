package br.propjeto_tech4.tech4.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AuditPrecoItemDTO(
        Integer itemId,
        BigDecimal precoAntigo,
        BigDecimal precoNovo,
        LocalDateTime dataAlteracao
) {
}
