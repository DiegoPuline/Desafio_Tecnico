package com.placeti.avaliacao.controller;

import com.placeti.avaliacao.dto.CidadeDTO;
import com.placeti.avaliacao.service.ProjetoService;
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
		// TODO: Responde GET em http://localhost:8080/placeti/cidades/1
	}
	
	//----------------------------------------------------------
	/** Endpoint que retorna todas as cidades cadastradas */
	//----------------------------------------------------------
	@GetMapping
	public List<CidadeDTO> pesquisarCidades() {
		// TODO: Responde GET em http://localhost:8080/placeti/cidades
	}
	
	//----------------------------------------------------------
	/** Endpoint para incluir nova cidade */
	//----------------------------------------------------------
	@PostMapping
	public void incluirCidade(@RequestBody CidadeDTO cidadeDto) {
		//	TODO: Responde POST em http://localhost:8080/placeti/cidades
		//	Envia JSON no body:
		//	{
		//	 	"nome": "Florianópolis",
		//	  	"uf": "SC",
		//	   	"capital": true
		//	}
	}	
	
	//----------------------------------------------------------
	/** Endpoint para alterar cidade */
	//----------------------------------------------------------
	@PutMapping
	public void alterarCidade(@RequestBody CidadeDTO cidadeDto) {
		// TODO: Responde PUT em http://localhost:8080/placeti/cidades
		//   Envia JSON no body:
		//   {
		//     "id": 11,
		//     "nome": "Blumenau",
		//     "uf": "SC",
		//     "capital": false
		//   }
	}
	
	//----------------------------------------------------------
	/** Endpoint para excluir uma cidade */
	//----------------------------------------------------------
	@DeleteMapping("/{idCidade}")
	public void excluirCidade(@PathVariable Long idCidade) {
		// Responde DELETE em http://localhost:8080/placeti/cidades/{idCidade}
	}	
}
