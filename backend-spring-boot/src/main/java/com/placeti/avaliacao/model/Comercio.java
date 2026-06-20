package com.placeti.avaliacao.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

//-------------------------------------------------
/** Entidade que guarda os dados de um comércio */
//-------------------------------------------------
@Data
@NoArgsConstructor
@Entity
@Table(name = "Comercio")
public class Comercio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NOME", length = 100, nullable = false)
    private String nome;

    @Column(name = "NOME_RESPONSAVEL", length = 100, nullable = false)
    private String nomeResponsavel;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO", length = 50, nullable = false)
    private TipoComercio tipo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "CIDADE_ID", nullable = false)
    private Cidade cidade;
}
