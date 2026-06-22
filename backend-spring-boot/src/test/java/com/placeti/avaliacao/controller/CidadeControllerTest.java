package com.placeti.avaliacao.controller;

import com.placeti.avaliacao.dto.CidadeDTO;
import com.placeti.avaliacao.service.ProjetoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CidadeController.class)
@Import(GlobalExceptionHandler.class)
class CidadeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProjetoService projetoService;

	@Test
	void buscarPeloId_deveRetornarCidadeComStatus200() throws Exception {
		CidadeDTO dto = new CidadeDTO(1L, "Goiânia", "GO", true);
		when(projetoService.pesquisarCidade(1L)).thenReturn(dto);

		mockMvc.perform(get("/cidades/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.nome").value("Goiânia"))
				.andExpect(jsonPath("$.uf").value("GO"))
				.andExpect(jsonPath("$.capital").value(true));
	}

	@Test
	void buscarPeloId_deveRetornar404QuandoCidadeNaoExistir() throws Exception {
		when(projetoService.pesquisarCidade(99L))
				.thenThrow(new EntityNotFoundException("Cidade não encontrada: 99"));

		mockMvc.perform(get("/cidades/99"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.mensagem").value("Cidade não encontrada: 99"));
	}

	@Test
	void pesquisarCidades_deveRetornarListaComStatus200() throws Exception {
		when(projetoService.pesquisarCidades()).thenReturn(List.of(
				new CidadeDTO(1L, "Goiânia", "GO", true),
				new CidadeDTO(2L, "Brasília", "DF", true)
		));

		mockMvc.perform(get("/cidades"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].nome").value("Goiânia"))
				.andExpect(jsonPath("$[1].nome").value("Brasília"));
	}

	@Test
	void incluirCidade_deveRetornar201QuandoDadosValidos() throws Exception {
		doNothing().when(projetoService).incluirCidade(any(CidadeDTO.class));

		mockMvc.perform(post("/cidades")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "nome": "Florianópolis",
								  "uf": "SC",
								  "capital": true
								}
								"""))
				.andExpect(status().isCreated());

		verify(projetoService).incluirCidade(any(CidadeDTO.class));
	}

	@Test
	void incluirCidade_deveRetornar400QuandoDadosInvalidos() throws Exception {
		mockMvc.perform(post("/cidades")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "nome": "",
								  "uf": "SC",
								  "capital": true
								}
								"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.mensagem").value("Erro de validação"))
				.andExpect(jsonPath("$.erros.nome").value("O nome é obrigatório"));
	}

	@Test
	void incluirCidade_deveRetornar400QuandoIdInformadoNaInclusao() throws Exception {
		mockMvc.perform(post("/cidades")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "id": 1,
								  "nome": "Florianópolis",
								  "uf": "SC",
								  "capital": true
								}
								"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros.id").value("O ID não deve ser informado na inclusão"));
	}

	@Test
	void alterarCidade_deveRetornar204QuandoDadosValidos() throws Exception {
		doNothing().when(projetoService).alterarCidade(any(CidadeDTO.class));

		mockMvc.perform(put("/cidades")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "id": 1,
								  "nome": "Anápolis",
								  "uf": "GO",
								  "capital": false
								}
								"""))
				.andExpect(status().isNoContent());

		verify(projetoService).alterarCidade(any(CidadeDTO.class));
	}

	@Test
	void alterarCidade_deveRetornar400QuandoIdNaoInformado() throws Exception {
		mockMvc.perform(put("/cidades")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "nome": "Anápolis",
								  "uf": "GO",
								  "capital": false
								}
								"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros.id").value("O ID é obrigatório na alteração"));
	}

	@Test
	void excluirCidade_deveRetornar204QuandoCidadeExistir() throws Exception {
		doNothing().when(projetoService).excluirCidade(1L);

		mockMvc.perform(delete("/cidades/1"))
				.andExpect(status().isNoContent());

		verify(projetoService).excluirCidade(1L);
	}

	@Test
	void excluirCidade_deveRetornar404QuandoCidadeNaoExistir() throws Exception {
		doThrow(new EntityNotFoundException("Cidade não encontrada: 99"))
				.when(projetoService).excluirCidade(99L);

		mockMvc.perform(delete("/cidades/99"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.mensagem").value("Cidade não encontrada: 99"));
	}
}
