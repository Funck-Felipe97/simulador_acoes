package com.funck.softexpert.desafio.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.funck.softexpert.desafio.model.Conta;
import com.funck.softexpert.desafio.model.Empresa;
import com.funck.softexpert.desafio.model.Monitoramento;
import com.funck.softexpert.desafio.repository.MonitoramentoRepository;

@Service
public class MonitoramentoService {

	@Autowired
	private MonitoramentoRepository monitoramentoRepository;
	
	@Autowired
	private ContaService contaService;

	public Optional<Monitoramento> findById(Long monitoramentoId) {
		return monitoramentoRepository.findById(monitoramentoId);
	}
	
	public Optional<Monitoramento> findByIdAndConta(Long monitoramentoId, Long contaId) throws Exception {
		Conta conta = contaService.findContaById(contaId);
		return monitoramentoRepository.findByIdAndConta(monitoramentoId, conta);
	}
	
	public List<Monitoramento> findAll() {
		return monitoramentoRepository.findAll();
	}
	
	public Set<Monitoramento> findAllByConta(Long contaId) throws Exception {
		Conta conta = contaService.findContaById(contaId);
		return monitoramentoRepository.findByConta(conta);
	}

	public Monitoramento save(Monitoramento monitoramento) {
		return monitoramentoRepository.save(monitoramento);
	}

	public Monitoramento update(Monitoramento monitoramento, Long monitoramentoId) throws Exception {
		Monitoramento monitoramentoSalvo = findMonitoramentoById(monitoramentoId);
		BeanUtils.copyProperties(monitoramento, monitoramentoSalvo, "id");
		return monitoramentoRepository.saveAndFlush(monitoramentoSalvo);
	}

	public void delete(Long id) {
		monitoramentoRepository.deleteById(id);
	}
	
	public Monitoramento findMonitoramentoById(Long monitoramentoId) throws Exception {
		Optional<Monitoramento> monitoramentoOptional = monitoramentoRepository.findById(monitoramentoId);
		
		if (!monitoramentoOptional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Monitoramento inexistente");
		}
		
		return monitoramentoOptional.get();
	}

	public Set<Monitoramento> findByEmpresa(Empresa empresa) {
		return monitoramentoRepository.findByEmpresa(empresa);
	}

}
