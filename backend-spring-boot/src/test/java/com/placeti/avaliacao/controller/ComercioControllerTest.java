package com.placeti.avaliacao.controller;

import com.placeti.avaliacao.dto.ComercioDTO;
import com.placeti.avaliacao.model.TipoComercio;
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

@WebMvcTest(ComercioController.class)
@Import(GlobalExceptionHandler.class)
class ComercioControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProjetoService projetoService;

	@Test
	void buscarPeloId_deveRetornarComercioComStatus200() throws Exception {
		ComercioDTO dto = new ComercioDTO(1L, "Farmácia Central", "Maria", TipoComercio.FARMACIA, 1L);
		when(projetoService.pesquisarComercio(1L)).thenReturn(dto);

		mockMvc.perform(get("/comercios/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.nome").value("Farmácia Central"))
				.andExpect(jsonPath("$.nomeResponsavel").value("Maria"))
				.andExpect(jsonPath("$.tipo").value("FARMACIA"))
				.andExpect(jsonPath("$.cidadeId").value(1));
	}

	@Test
	void buscarPeloId_deveRetornar404QuandoComercioNaoExistir() throws Exception {
		when(projetoService.pesquisarComercio(99L))
				.thenThrow(new EntityNotFoundException("Comércio não encontrado: 99"));

		mockMvc.perform(get("/comercios/99"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.mensagem").value("Comércio não encontrado: 99"));
	}

	@Test
	void pesquisarComercios_deveRetornarListaComStatus200() throws Exception {
		when(projetoService.pesquisarComercios()).thenReturn(List.of(
				new ComercioDTO(1L, "Padaria Sol", "João", TipoComercio.PADARIA, 1L)
		));

		mockMvc.perform(get("/comercios"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1))
				.andExpect(jsonPath("$[0].nome").value("Padaria Sol"));
	}

	@Test
	void incluirComercio_deveRetornar201QuandoDadosValidos() throws Exception {
		doNothing().when(projetoService).incluirComercio(any(ComercioDTO.class));

		mockMvc.perform(post("/comercios")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "nome": "Posto Norte",
								  "nomeResponsavel": "Carlos",
								  "tipo": "POSTO_GASOLINA",
								  "cidadeId": 1
								}
								"""))
				.andExpect(status().isCreated());

		verify(projetoService).incluirComercio(any(ComercioDTO.class));
	}

	@Test
	void incluirComercio_deveRetornar400QuandoNomeResponsavelAusente() throws Exception {
		mockMvc.perform(post("/comercios")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "nome": "Posto Norte",
								  "tipo": "POSTO_GASOLINA",
								  "cidadeId": 1
								}
								"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.erros.nomeResponsavel").value("O nome do responsável é obrigatório"));
	}

	@Test
	void incluirComercio_deveRetornar400QuandoIdInformadoNaInclusao() throws Exception {
		mockMvc.perform(post("/comercios")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "id": 1,
								  "nome": "Lanchonete",
								  "nomeResponsavel": "Ana",
								  "tipo": "LANCHONETE",
								  "cidadeId": 1
								}
								"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros.id").value("O ID não deve ser informado na inclusão"));
	}

	@Test
	void alterarComercio_deveRetornar204QuandoDadosValidos() throws Exception {
		doNothing().when(projetoService).alterarComercio(any(ComercioDTO.class));

		mockMvc.perform(put("/comercios")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "id": 1,
								  "nome": "Farmácia Nova",
								  "nomeResponsavel": "Paula",
								  "tipo": "FARMACIA",
								  "cidadeId": 1
								}
								"""))
				.andExpect(status().isNoContent());

		verify(projetoService).alterarComercio(any(ComercioDTO.class));
	}

	@Test
	void alterarComercio_deveRetornar400QuandoIdNaoInformado() throws Exception {
		mockMvc.perform(put("/comercios")
						.contentType(MediaType.APPLICATION_JSON)
						.content("""
								{
								  "nome": "Farmácia Nova",
								  "nomeResponsavel": "Paula",
								  "tipo": "FARMACIA",
								  "cidadeId": 1
								}
								"""))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.erros.id").value("O ID é obrigatório na alteração"));
	}

	@Test
	void excluirComercio_deveRetornar204QuandoComercioExistir() throws Exception {
		doNothing().when(projetoService).excluirComercio(1L);

		mockMvc.perform(delete("/comercios/1"))
				.andExpect(status().isNoContent());

		verify(projetoService).excluirComercio(1L);
	}

	@Test
	void excluirComercio_deveRetornar404QuandoComercioNaoExistir() throws Exception {
		doThrow(new EntityNotFoundException("Comércio não encontrado: 99"))
				.when(projetoService).excluirComercio(99L);

		mockMvc.perform(delete("/comercios/99"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.mensagem").value("Comércio não encontrado: 99"));
	}
}
