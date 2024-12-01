package br.propjeto_tech4.tech4.modal;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pagamento_id")
    private Integer pagamentoId;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "forma_pgto_id", nullable = false)
    private FormaPgto formaPgto;

    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    public Pagamento() {
    }

    public Pagamento(Pedido pedido, FormaPgto formaPgto, BigDecimal valor) {
        this.pedido = pedido;
        this.formaPgto = formaPgto;
        this.valor = valor;
    }

    public Integer getPagamentoId() {
        return pagamentoId;
    }

    public void setPagamentoId(Integer pagamentoId) {
        this.pagamentoId = pagamentoId;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public FormaPgto getFormaPgto() {
        return formaPgto;
    }

    public void setFormaPgto(FormaPgto formaPgto) {
        this.formaPgto = formaPgto;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Pagamento{" +
                "pagamentoId=" + pagamentoId +
                ", pedido=" + pedido +
                ", formaPgto=" + formaPgto +
                ", valor=" + valor +
                '}';
    }
}
