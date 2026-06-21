package com.placeti.avaliacao.service;

import com.placeti.avaliacao.dto.CidadeDTO;
import com.placeti.avaliacao.dto.ComercioDTO;
import com.placeti.avaliacao.repository.CidadeRepository;
import com.placeti.avaliacao.repository.ComercioRepository;
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
	}

	//---------------------------------------------------------
	/** Método que retorna todas as cidades cadastradas */
	//---------------------------------------------------------
	public List<CidadeDTO> pesquisarCidades() {

	}

	//----------------------------------------------------------
	/** Método chamado para incluir uma nova cidade */
	//----------------------------------------------------------	
	public void incluirCidade(CidadeDTO dto) {
		
	}

	//----------------------------------------------------------
	/** Método chamado para alterar os dados de uma cidade */
	//----------------------------------------------------------
	public void alterarCidade(CidadeDTO dto) {

	}

	//----------------------------------------------------------
	/** Método chamado para excluir uma cidade */
	//----------------------------------------------------------	
	public void excluirCidade(Long idCidade) {

	}

	//---------------------------------------------------------
	/** Método que busca um comércio pelo seu ID */
	//---------------------------------------------------------
	public ComercioDTO pesquisarComercio(Long id) {
	}

	//---------------------------------------------------------
	/** Método que retorna todos os comércios cadastrados */
	//---------------------------------------------------------
	public List<ComercioDTO> pesquisarComercios() {

	}

	//----------------------------------------------------------
	/** Método chamado para incluir um novo comércio */
	//----------------------------------------------------------
	public void incluirComercio(ComercioDTO dto) {

	}

	//----------------------------------------------------------
	/** Método chamado para alterar os dados de um comércio */
	//----------------------------------------------------------
	public void alterarComercio(ComercioDTO dto) {

	}

	//----------------------------------------------------------
	/** Método chamado para excluir um comércio */
	//----------------------------------------------------------
	public void excluirComercio(Long idComercio) {

	}
}
