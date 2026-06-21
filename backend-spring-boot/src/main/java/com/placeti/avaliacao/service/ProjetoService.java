package com.placeti.avaliacao.service;

import com.placeti.avaliacao.dto.CidadeDTO;
import com.placeti.avaliacao.dto.ComercioDTO;
import com.placeti.avaliacao.model.Cidade;
import com.placeti.avaliacao.model.Comercio;
import com.placeti.avaliacao.repository.CidadeRepository;
import com.placeti.avaliacao.repository.ComercioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

//------------------------------------------------------------------
/** Service usado para acessar os repositórios da aplicação */
//------------------------------------------------------------------
@Service
public class ProjetoService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final CidadeRepository cidadeRepository;
	private final ComercioRepository comercioRepository;

	public ProjetoService(CidadeRepository cidadeRepository, ComercioRepository comercioRepository) {
		this.cidadeRepository = cidadeRepository;
		this.comercioRepository = comercioRepository;
	}

	//---------------------------------------------------------
	/** Método que busca uma cidade pelo seu ID */
	//---------------------------------------------------------
	public CidadeDTO pesquisarCidade(Long id) {
		logger.debug("Pesquisando cidade pelo ID {}", id);
		return cidadeRepository.findById(id)
				.map(this::toDto)
				.orElseThrow(() -> new EntityNotFoundException("Cidade não encontrada: " + id));
	}

	//---------------------------------------------------------
	/** Método que retorna todas as cidades cadastradas */
	//---------------------------------------------------------
	public List<CidadeDTO> pesquisarCidades() {
		logger.debug("Pesquisando todas as cidades");
		return cidadeRepository.findAll().stream()
				.map(this::toDto)
				.toList();
	}

	//----------------------------------------------------------
	/** Método chamado para incluir uma nova cidade */
	//----------------------------------------------------------
	public void incluirCidade(CidadeDTO dto) {
		logger.debug("Incluindo cidade {}", dto.nome());
		Cidade cidade = new Cidade();
		cidade.setNome(dto.nome());
		cidade.setUf(dto.uf());
		cidade.setCapital(dto.capital());
		cidadeRepository.save(cidade);
	}

	//----------------------------------------------------------
	/** Método chamado para alterar os dados de uma cidade */
	//----------------------------------------------------------
	public void alterarCidade(CidadeDTO dto) {
		logger.debug("Alterando cidade ID {}", dto.id());
		Cidade cidade = cidadeRepository.findById(dto.id())
				.orElseThrow(() -> new EntityNotFoundException("Cidade não encontrada: " + dto.id()));
		cidade.setNome(dto.nome());
		cidade.setUf(dto.uf());
		cidade.setCapital(dto.capital());
		cidadeRepository.save(cidade);
	}

	//----------------------------------------------------------
	/** Método chamado para excluir uma cidade */
	//----------------------------------------------------------
	public void excluirCidade(Long idCidade) {
		logger.debug("Excluindo cidade ID {}", idCidade);
		if (!cidadeRepository.existsById(idCidade)) {
			throw new EntityNotFoundException("Cidade não encontrada: " + idCidade);
		}
		cidadeRepository.deleteById(idCidade);
	}

	//---------------------------------------------------------
	/** Método que busca um comércio pelo seu ID */
	//---------------------------------------------------------
	public ComercioDTO pesquisarComercio(Long id) {
		logger.debug("Pesquisando comércio pelo ID {}", id);
		return comercioRepository.findById(id)
				.map(this::toDto)
				.orElseThrow(() -> new EntityNotFoundException("Comércio não encontrado: " + id));
	}

	//---------------------------------------------------------
	/** Método que retorna todos os comércios cadastrados */
	//---------------------------------------------------------
	public List<ComercioDTO> pesquisarComercios() {
		logger.debug("Pesquisando todos os comércios");
		return comercioRepository.findAll().stream()
				.map(this::toDto)
				.toList();
	}

	//----------------------------------------------------------
	/** Método chamado para incluir um novo comércio */
	//----------------------------------------------------------
	public void incluirComercio(ComercioDTO dto) {
		logger.debug("Incluindo comércio {}", dto.nome());
		Cidade cidade = buscarCidade(dto.cidadeId());
		Comercio comercio = new Comercio();
		comercio.setNome(dto.nome());
		comercio.setNomeResponsavel(dto.nomeResponsavel());
		comercio.setTipo(dto.tipo());
		comercio.setCidade(cidade);
		comercioRepository.save(comercio);
	}

	//----------------------------------------------------------
	/** Método chamado para alterar os dados de um comércio */
	//----------------------------------------------------------
	public void alterarComercio(ComercioDTO dto) {
		logger.debug("Alterando comércio ID {}", dto.id());
		Comercio comercio = comercioRepository.findById(dto.id())
				.orElseThrow(() -> new EntityNotFoundException("Comércio não encontrado: " + dto.id()));
		Cidade cidade = buscarCidade(dto.cidadeId());
		comercio.setNome(dto.nome());
		comercio.setNomeResponsavel(dto.nomeResponsavel());
		comercio.setTipo(dto.tipo());
		comercio.setCidade(cidade);
		comercioRepository.save(comercio);
	}

	//----------------------------------------------------------
	/** Método chamado para excluir um comércio */
	//----------------------------------------------------------
	public void excluirComercio(Long idComercio) {
		logger.debug("Excluindo comércio ID {}", idComercio);
		if (!comercioRepository.existsById(idComercio)) {
			throw new EntityNotFoundException("Comércio não encontrado: " + idComercio);
		}
		comercioRepository.deleteById(idComercio);
	}

	private Cidade buscarCidade(Long cidadeId) {
		return cidadeRepository.findById(cidadeId)
				.orElseThrow(() -> new EntityNotFoundException("Cidade não encontrada: " + cidadeId));
	}

	private CidadeDTO toDto(Cidade cidade) {
		return new CidadeDTO(cidade.getId(), cidade.getNome(), cidade.getUf(), cidade.getCapital());
	}

	private ComercioDTO toDto(Comercio comercio) {
		return new ComercioDTO(
				comercio.getId(),
				comercio.getNome(),
				comercio.getNomeResponsavel(),
				comercio.getTipo(),
				comercio.getCidade().getId()
		);
	}
}
