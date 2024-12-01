package br.propjeto_tech4.tech4.modal;

import jakarta.persistence.*;

@Entity
@Table(name = "Forma_Pgto")
public class FormaPgto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "forma_pgto_id")
    private Integer formaPgtoId;

    @Column(name = "descricao", nullable = false, length = 50)
    private String descricao;

    public FormaPgto() {
    }

    public FormaPgto(String descricao) {
        this.descricao = descricao;
    }

    public Integer getFormaPgtoId() {
        return formaPgtoId;
    }

    public void setFormaPgtoId(Integer formaPgtoId) {
        this.formaPgtoId = formaPgtoId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "FormaPgto{" +
                "formaPgtoId=" + formaPgtoId +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}