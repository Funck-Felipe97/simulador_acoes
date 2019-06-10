package com.funck.softexpert.desafio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.funck.softexpert.desafio.model.Empresa;
import com.funck.softexpert.desafio.repository.EmpresaRepository;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private NegociacaoAcaoService negociacaoAcaoService;

	public Optional<Empresa> findById(Long empresaId) {
		return empresaRepository.findById(empresaId);
	}

	public List<Empresa> findAll() {
		return empresaRepository.findAll();
	}

	public Empresa save(Empresa empresa) {
		return empresaRepository.save(empresa);
	}

	public Empresa update(Empresa empresa, Long id) throws Exception {
		Optional<Empresa> empresaOptional = empresaRepository.findById(id);

		if (!empresaOptional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa inexistente");
		}

		BeanUtils.copyProperties(empresa, empresaOptional.get(), "id");
		Empresa empresaSalva = empresaRepository.save(empresaOptional.get());
		
		negociacaoAcaoService.notifyChangeEmpresa(empresaSalva);
		return empresaSalva;
	}

}
