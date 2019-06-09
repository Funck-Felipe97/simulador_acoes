package com.funck.softexpert.desafio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funck.softexpert.desafio.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

}
