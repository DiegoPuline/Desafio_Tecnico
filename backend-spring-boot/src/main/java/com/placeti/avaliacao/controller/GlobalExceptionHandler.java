package com.placeti.avaliacao.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

//--------------------------------------------------
/** Tratamento centralizado de exceções da API */
//--------------------------------------------------
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> erros = ex.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(
						FieldError::getField,
						FieldError::getDefaultMessage,
						(mensagemAtual, mensagemNova) -> mensagemAtual
				));

		Map<String, Object> resposta = new LinkedHashMap<>();
		resposta.put("status", HttpStatus.BAD_REQUEST.value());
		resposta.put("mensagem", "Erro de validação");
		resposta.put("erros", erros);

		return ResponseEntity.badRequest().body(resposta);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleEntityNotFoundException(EntityNotFoundException ex) {
		Map<String, Object> resposta = new LinkedHashMap<>();
		resposta.put("status", HttpStatus.NOT_FOUND.value());
		resposta.put("mensagem", ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
	}
}
