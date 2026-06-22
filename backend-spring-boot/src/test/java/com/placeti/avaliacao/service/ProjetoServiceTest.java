package com.placeti.avaliacao.service;

import com.placeti.avaliacao.dto.CidadeDTO;
import com.placeti.avaliacao.dto.ComercioDTO;
import com.placeti.avaliacao.model.Cidade;
import com.placeti.avaliacao.model.Comercio;
import com.placeti.avaliacao.model.TipoComercio;
import com.placeti.avaliacao.repository.CidadeRepository;
import com.placeti.avaliacao.repository.ComercioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjetoServiceTest {

	@Mock
	private CidadeRepository cidadeRepository;

	@Mock
	private ComercioRepository comercioRepository;

	@InjectMocks
	private ProjetoService projetoService;

	@Test
	void pesquisarCidade_deveRetornarDtoQuandoCidadeExistir() {
		Cidade cidade = criarCidade(1L, "Goiânia", "GO", true);
		when(cidadeRepository.findById(1L)).thenReturn(Optional.of(cidade));

		CidadeDTO resultado = projetoService.pesquisarCidade(1L);

		assertEquals(1L, resultado.id());
		assertEquals("Goiânia", resultado.nome());
		assertEquals("GO", resultado.uf());
		assertEquals(true, resultado.capital());
	}

	@Test
	void pesquisarCidade_deveLancarExcecaoQuandoCidadeNaoExistir() {
		when(cidadeRepository.findById(99L)).thenReturn(Optional.empty());

		EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
				() -> projetoService.pesquisarCidade(99L));

		assertEquals("Cidade não encontrada: 99", ex.getMessage());
	}

	@Test
	void pesquisarCidades_deveRetornarListaDeDtos() {
		when(cidadeRepository.findAll()).thenReturn(List.of(
				criarCidade(1L, "Goiânia", "GO", true),
				criarCidade(2L, "Brasília", "DF", true)
		));

		List<CidadeDTO> resultado = projetoService.pesquisarCidades();

		assertEquals(2, resultado.size());
		assertEquals("Goiânia", resultado.get(0).nome());
		assertEquals("Brasília", resultado.get(1).nome());
	}

	@Test
	void incluirCidade_deveSalvarNovaCidade() {
		CidadeDTO dto = new CidadeDTO(null, "Florianópolis", "SC", true);

		projetoService.incluirCidade(dto);

		ArgumentCaptor<Cidade> captor = ArgumentCaptor.forClass(Cidade.class);
		verify(cidadeRepository).save(captor.capture());
		assertEquals("Florianópolis", captor.getValue().getNome());
		assertEquals("SC", captor.getValue().getUf());
		assertEquals(true, captor.getValue().getCapital());
	}

	@Test
	void alterarCidade_deveAtualizarCidadeExistente() {
		Cidade cidade = criarCidade(1L, "Goiânia", "GO", true);
		CidadeDTO dto = new CidadeDTO(1L, "Anápolis", "GO", false);
		when(cidadeRepository.findById(1L)).thenReturn(Optional.of(cidade));

		projetoService.alterarCidade(dto);

		assertEquals("Anápolis", cidade.getNome());
		assertEquals(false, cidade.getCapital());
		verify(cidadeRepository).save(cidade);
	}

	@Test
	void alterarCidade_deveLancarExcecaoQuandoCidadeNaoExistir() {
		CidadeDTO dto = new CidadeDTO(99L, "Inexistente", "GO", false);
		when(cidadeRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> projetoService.alterarCidade(dto));
		verify(cidadeRepository, never()).save(any());
	}

	@Test
	void excluirCidade_deveRemoverCidadeExistente() {
		when(cidadeRepository.existsById(1L)).thenReturn(true);

		projetoService.excluirCidade(1L);

		verify(cidadeRepository).deleteById(1L);
	}

	@Test
	void excluirCidade_deveLancarExcecaoQuandoCidadeNaoExistir() {
		when(cidadeRepository.existsById(99L)).thenReturn(false);

		assertThrows(EntityNotFoundException.class, () -> projetoService.excluirCidade(99L));
		verify(cidadeRepository, never()).deleteById(99L);
	}

	@Test
	void pesquisarComercio_deveRetornarDtoQuandoComercioExistir() {
		Cidade cidade = criarCidade(1L, "Goiânia", "GO", true);
		Comercio comercio = criarComercio(1L, "Farmácia Central", "Maria", TipoComercio.FARMACIA, cidade);
		when(comercioRepository.findById(1L)).thenReturn(Optional.of(comercio));

		ComercioDTO resultado = projetoService.pesquisarComercio(1L);

		assertEquals(1L, resultado.id());
		assertEquals("Farmácia Central", resultado.nome());
		assertEquals("Maria", resultado.nomeResponsavel());
		assertEquals(TipoComercio.FARMACIA, resultado.tipo());
		assertEquals(1L, resultado.cidadeId());
	}

	@Test
	void pesquisarComercio_deveLancarExcecaoQuandoComercioNaoExistir() {
		when(comercioRepository.findById(99L)).thenReturn(Optional.empty());

		EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
				() -> projetoService.pesquisarComercio(99L));

		assertEquals("Comércio não encontrado: 99", ex.getMessage());
	}

	@Test
	void pesquisarComercios_deveRetornarListaDeDtos() {
		Cidade cidade = criarCidade(1L, "Goiânia", "GO", true);
		when(comercioRepository.findAll()).thenReturn(List.of(
				criarComercio(1L, "Padaria Sol", "João", TipoComercio.PADARIA, cidade)
		));

		List<ComercioDTO> resultado = projetoService.pesquisarComercios();

		assertEquals(1, resultado.size());
		assertEquals("Padaria Sol", resultado.get(0).nome());
	}

	@Test
	void incluirComercio_deveSalvarComercioComCidadeValida() {
		Cidade cidade = criarCidade(1L, "Goiânia", "GO", true);
		ComercioDTO dto = new ComercioDTO(null, "Posto Norte", "Carlos", TipoComercio.POSTO_GASOLINA, 1L);
		when(cidadeRepository.findById(1L)).thenReturn(Optional.of(cidade));

		projetoService.incluirComercio(dto);

		ArgumentCaptor<Comercio> captor = ArgumentCaptor.forClass(Comercio.class);
		verify(comercioRepository).save(captor.capture());
		assertEquals("Posto Norte", captor.getValue().getNome());
		assertEquals(TipoComercio.POSTO_GASOLINA, captor.getValue().getTipo());
		assertEquals(cidade, captor.getValue().getCidade());
	}

	@Test
	void incluirComercio_deveLancarExcecaoQuandoCidadeNaoExistir() {
		ComercioDTO dto = new ComercioDTO(null, "Lanchonete", "Ana", TipoComercio.LANCHONETE, 99L);
		when(cidadeRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> projetoService.incluirComercio(dto));
		verify(comercioRepository, never()).save(any());
	}

	@Test
	void alterarComercio_deveAtualizarComercioExistente() {
		Cidade cidade = criarCidade(1L, "Goiânia", "GO", true);
		Comercio comercio = criarComercio(1L, "Farmácia", "Pedro", TipoComercio.FARMACIA, cidade);
		ComercioDTO dto = new ComercioDTO(1L, "Farmácia Nova", "Paula", TipoComercio.FARMACIA, 1L);
		when(comercioRepository.findById(1L)).thenReturn(Optional.of(comercio));
		when(cidadeRepository.findById(1L)).thenReturn(Optional.of(cidade));

		projetoService.alterarComercio(dto);

		assertEquals("Farmácia Nova", comercio.getNome());
		assertEquals("Paula", comercio.getNomeResponsavel());
		verify(comercioRepository).save(comercio);
	}

	@Test
	void alterarComercio_deveLancarExcecaoQuandoComercioNaoExistir() {
		ComercioDTO dto = new ComercioDTO(99L, "Inexistente", "Teste", TipoComercio.PADARIA, 1L);
		when(comercioRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(EntityNotFoundException.class, () -> projetoService.alterarComercio(dto));
		verify(comercioRepository, never()).save(any());
	}

	@Test
	void excluirComercio_deveRemoverComercioExistente() {
		when(comercioRepository.existsById(1L)).thenReturn(true);

		projetoService.excluirComercio(1L);

		verify(comercioRepository).deleteById(1L);
	}

	@Test
	void excluirComercio_deveLancarExcecaoQuandoComercioNaoExistir() {
		when(comercioRepository.existsById(99L)).thenReturn(false);

		assertThrows(EntityNotFoundException.class, () -> projetoService.excluirComercio(99L));
		verify(comercioRepository, never()).deleteById(99L);
	}

	private Cidade criarCidade(Long id, String nome, String uf, boolean capital) {
		Cidade cidade = new Cidade();
		cidade.setId(id);
		cidade.setNome(nome);
		cidade.setUf(uf);
		cidade.setCapital(capital);
		return cidade;
	}

	private Comercio criarComercio(Long id, String nome, String responsavel, TipoComercio tipo, Cidade cidade) {
		Comercio comercio = new Comercio();
		comercio.setId(id);
		comercio.setNome(nome);
		comercio.setNomeResponsavel(responsavel);
		comercio.setTipo(tipo);
		comercio.setCidade(cidade);
		return comercio;
	}
}
