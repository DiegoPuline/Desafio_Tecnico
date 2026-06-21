package com.placeti.avaliacao.dto;

import com.placeti.avaliacao.model.TipoComercio;

/**
 * DTO que guarda os dados de um comércio
 */
public record ComercioDTO(

        //---------------------------------------
        // Atributos do DTO
        //---------------------------------------
        Long id,
        String nome,
        String nomeResponsavel,
        TipoComercio tipo,
        Long cidadeId

) {
}
