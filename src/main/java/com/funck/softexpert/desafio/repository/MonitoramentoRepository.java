package com.funck.softexpert.desafio.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funck.softexpert.desafio.model.Conta;
import com.funck.softexpert.desafio.model.Empresa;
import com.funck.softexpert.desafio.model.Monitoramento;

public interface MonitoramentoRepository extends JpaRepository<Monitoramento, Long>{

	Optional<Monitoramento> findByIdAndConta(Long id, Conta conta);

	Set<Monitoramento> findByConta(Conta conta);

	Set<Monitoramento> findByEmpresa(Empresa empresa);

}
