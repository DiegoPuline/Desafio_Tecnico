package com.placeti.avaliacao.controller;

import com.placeti.avaliacao.dto.CidadeDTO;
import com.placeti.avaliacao.service.ProjetoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//--------------------------------------------------
/** Endpoint para consultar e manter cidades */
//--------------------------------------------------
@RestController
@RequestMapping("/cidades")
public class CidadeController {

	private final ProjetoService projetoService;

	public CidadeController(ProjetoService projetoService) {
		this.projetoService = projetoService;
	}

	//----------------------------------------------------------
	/** Endpoint que retorna uma cidade conforme seu ID */
	//----------------------------------------------------------
	@GetMapping("/{id}")
	public ResponseEntity<CidadeDTO> buscarPeloId(@PathVariable Long id) {
		return ResponseEntity.ok(projetoService.pesquisarCidade(id));
	}
	
	//----------------------------------------------------------
	/** Endpoint que retorna todas as cidades cadastradas */
	//----------------------------------------------------------
	@GetMapping
	public List<CidadeDTO> pesquisarCidades() {
		return projetoService.pesquisarCidades();
	}
	
	//----------------------------------------------------------
	/** Endpoint para incluir nova cidade */
	//----------------------------------------------------------
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void incluirCidade(@RequestBody CidadeDTO cidadeDto) {
		projetoService.incluirCidade(cidadeDto);
	}	
	
	//----------------------------------------------------------
	/** Endpoint para alterar cidade */
	//----------------------------------------------------------
	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarCidade(@RequestBody CidadeDTO cidadeDto) {
		projetoService.alterarCidade(cidadeDto);
	}
	
	//----------------------------------------------------------
	/** Endpoint para excluir uma cidade */
	//----------------------------------------------------------
	@DeleteMapping("/{idCidade}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluirCidade(@PathVariable Long idCidade) {
		projetoService.excluirCidade(idCidade);
	}	
}
