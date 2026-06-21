package com.placeti.avaliacao.repository;

import com.placeti.avaliacao.model.Comercio;
import org.springframework.data.jpa.repository.JpaRepository;

//----------------------------------------------
/** Repositório para entidade Comercio */
//----------------------------------------------
public interface ComercioRepository extends JpaRepository<Comercio, Long> {
}
