package br.propjeto_tech4.tech4.modal;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Audit_Preco_Item")
public class AuditPrecoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")
    private Integer auditId;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(name = "preco_antigo", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoAntigo;

    @Column(name = "preco_novo", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoNovo;

    @Column(name = "data_alteracao", nullable = false)
    private LocalDateTime dataAlteracao;

    public AuditPrecoItem() {
    }

    public AuditPrecoItem(Item item, BigDecimal precoAntigo, BigDecimal precoNovo, LocalDateTime dataAlteracao) {
        this.item = item;
        this.precoAntigo = precoAntigo;
        this.precoNovo = precoNovo;
        this.dataAlteracao = dataAlteracao;
    }

    public Integer getAuditId() {
        return auditId;
    }

    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BigDecimal getPrecoAntigo() {
        return precoAntigo;
    }

    public void setPrecoAntigo(BigDecimal precoAntigo) {
        this.precoAntigo = precoAntigo;
    }

    public BigDecimal getPrecoNovo() {
        return precoNovo;
    }

    public void setPrecoNovo(BigDecimal precoNovo) {
        this.precoNovo = precoNovo;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    @Override
    public String toString() {
        return "AuditPrecoItem{" +
                "auditId=" + auditId +
                ", item=" + item +
                ", precoAntigo=" + precoAntigo +
                ", precoNovo=" + precoNovo +
                ", dataAlteracao=" + dataAlteracao +
                '}';
    }
}
