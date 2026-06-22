package com.placeti.avaliacao.dto;

import com.placeti.avaliacao.model.TipoComercio;
import com.placeti.avaliacao.validation.OnCreate;
import com.placeti.avaliacao.validation.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

/**
 * DTO que guarda os dados de um comércio
 */
public record ComercioDTO(

        //---------------------------------------
        // Atributos do DTO
        //---------------------------------------
        @Null(groups = OnCreate.class, message = "O ID não deve ser informado na inclusão")
        @NotNull(groups = OnUpdate.class, message = "O ID é obrigatório na alteração")
        Long id,

        @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "O nome do comércio é obrigatório")
        @Size(groups = {OnCreate.class, OnUpdate.class}, max = 100, message = "O nome do comércio deve ter no máximo 100 caracteres")
        String nome,

        @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "O nome do responsável é obrigatório")
        @Size(groups = {OnCreate.class, OnUpdate.class}, max = 100, message = "O nome do responsável deve ter no máximo 100 caracteres")
        String nomeResponsavel,

        @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "O tipo de comércio é obrigatório")
        TipoComercio tipo,

        @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "O ID da cidade é obrigatório")
        Long cidadeId

) {
}
