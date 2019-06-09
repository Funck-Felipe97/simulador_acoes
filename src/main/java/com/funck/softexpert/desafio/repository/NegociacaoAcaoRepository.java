package com.funck.softexpert.desafio.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funck.softexpert.desafio.model.Conta;
import com.funck.softexpert.desafio.model.NegociacaoAcao;

public interface NegociacaoAcaoRepository extends JpaRepository<NegociacaoAcao, Long> {

	public Set<NegociacaoAcao> findByConta(Conta conta);

	public Optional<NegociacaoAcao> findByIdAndConta(Long id, Conta conta);
	
}
