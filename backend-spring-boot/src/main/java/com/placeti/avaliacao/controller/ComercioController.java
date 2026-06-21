package com.placeti.avaliacao.controller;

import com.placeti.avaliacao.dto.ComercioDTO;
import com.placeti.avaliacao.service.ProjetoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//--------------------------------------------------
/** Endpoint para consultar e manter comércios */
//--------------------------------------------------
@RestController
@RequestMapping("/comercios")
public class ComercioController {

	private final ProjetoService projetoService;

	public ComercioController(ProjetoService projetoService) {
		this.projetoService = projetoService;
	}

	//----------------------------------------------------------
	/** Endpoint que retorna um comércio conforme seu ID */
	//----------------------------------------------------------
	@GetMapping("/{id}")
	public ResponseEntity<ComercioDTO> buscarPeloId(@PathVariable Long id) {
		// TODO: Responde GET em http://localhost:8080/placeti/comercios/1
	}

	//----------------------------------------------------------
	/** Endpoint que retorna todos os comércios cadastrados */
	//----------------------------------------------------------
	@GetMapping
	public List<ComercioDTO> pesquisarComercios() {
		// TODO: Responde GET em http://localhost:8080/placeti/comercios
	}

	//----------------------------------------------------------
	/** Endpoint para incluir novo comércio */
	//----------------------------------------------------------
	@PostMapping
	public void incluirComercio(@RequestBody ComercioDTO comercioDto) {
		// TODO: Responde POST em http://localhost:8080/placeti/comercios
	}

	//----------------------------------------------------------
	/** Endpoint para alterar comércio */
	//----------------------------------------------------------
	@PutMapping
	public void alterarComercio(@RequestBody ComercioDTO comercioDto) {
		// TODO: Responde PUT em http://localhost:8080/placeti/comercios
	}

	//----------------------------------------------------------
	/** Endpoint para excluir um comércio */
	//----------------------------------------------------------
	@DeleteMapping("/{idComercio}")
	public void excluirComercio(@PathVariable Long idComercio) {
		// TODO: Responde DELETE em http://localhost:8080/placeti/comercios/{idComercio}
	}
}
