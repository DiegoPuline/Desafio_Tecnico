package com.placeti.avaliacao.controller;

import com.placeti.avaliacao.dto.ComercioDTO;
import com.placeti.avaliacao.service.ProjetoService;
import com.placeti.avaliacao.validation.OnCreate;
import com.placeti.avaliacao.validation.OnUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//--------------------------------------------------
/** Endpoint para consultar e manter comércios */
//--------------------------------------------------
@RestController
@RequestMapping("/comercios")
@Validated
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
		return ResponseEntity.ok(projetoService.pesquisarComercio(id));
	}

	//----------------------------------------------------------
	/** Endpoint que retorna todos os comércios cadastrados */
	//----------------------------------------------------------
	@GetMapping
	public List<ComercioDTO> pesquisarComercios() {
		return projetoService.pesquisarComercios();
	}

	//----------------------------------------------------------
	/** Endpoint para incluir novo comércio */
	//----------------------------------------------------------
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void incluirComercio(@RequestBody @Validated(OnCreate.class) ComercioDTO comercioDto) {
		projetoService.incluirComercio(comercioDto);
	}

	//----------------------------------------------------------
	/** Endpoint para alterar comércio */
	//----------------------------------------------------------
	@PutMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarComercio(@RequestBody @Validated(OnUpdate.class) ComercioDTO comercioDto) {
		projetoService.alterarComercio(comercioDto);
	}

	//----------------------------------------------------------
	/** Endpoint para excluir um comércio */
	//----------------------------------------------------------
	@DeleteMapping("/{idComercio}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluirComercio(@PathVariable Long idComercio) {
		projetoService.excluirComercio(idComercio);
	}
}
