package com.walmircastro.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.walmircastro.cursomc.domain.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

}
