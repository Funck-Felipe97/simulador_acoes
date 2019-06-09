package com.funck.softexpert.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funck.softexpert.desafio.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {

}
