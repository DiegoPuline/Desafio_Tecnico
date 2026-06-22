package com.placeti.avaliacao.dto;

import com.placeti.avaliacao.validation.OnCreate;
import com.placeti.avaliacao.validation.OnUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

/**
 * DTO que guarda os dados de uma cidade
 */
public record CidadeDTO(

        //---------------------------------------
        // Atributos do DTO
        //---------------------------------------
        @Null(groups = OnCreate.class, message = "O ID não deve ser informado na inclusão")
        @NotNull(groups = OnUpdate.class, message = "O ID é obrigatório na alteração")
        Long id,

        @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "O nome é obrigatório")
        @Size(groups = {OnCreate.class, OnUpdate.class}, max = 100, message = "O nome deve ter no máximo 100 caracteres")
        String nome,

        @NotBlank(groups = {OnCreate.class, OnUpdate.class}, message = "A UF é obrigatória")
        @Size(groups = {OnCreate.class, OnUpdate.class}, min = 2, max = 2, message = "A UF deve conter exatamente 2 caracteres")
        String uf,

        @NotNull(groups = {OnCreate.class, OnUpdate.class}, message = "O campo capital é obrigatório")
        Boolean capital

) {
}
