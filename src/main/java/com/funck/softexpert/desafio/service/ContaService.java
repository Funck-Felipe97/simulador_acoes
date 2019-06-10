package com.funck.softexpert.desafio.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.funck.softexpert.desafio.model.Conta;
import com.funck.softexpert.desafio.model.Monitoramento;
import com.funck.softexpert.desafio.model.NegociacaoAcao;
import com.funck.softexpert.desafio.repository.ContaRepository;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;
		
	public Optional<Conta> findById(Long contaId) {
		return this.contaRepository.findById(contaId);
	}

	public List<Conta> findAll() {
		return this.contaRepository.findAll();
	}

	public Conta save(Conta conta) {
		return this.contaRepository.save(conta);
	}

	public Set<NegociacaoAcao> findNegociacoesByConta(Long contaId) throws Exception {
		Conta conta = findContaById(contaId);
		return conta.getNegociacoes();
	}
	
	public Set<Monitoramento> findMonitoramentosByConta(Long contaId) throws Exception {
		Conta conta = findContaById(contaId);
		return conta.getMonitoramentos();
	}

	public List<NegociacaoAcao> findComprasByConta(Long contaId) throws Exception {
		Conta conta = findContaById(contaId);
		return conta.getCompras();
	}

	public List<NegociacaoAcao> findVendasByConta(Long contaId) throws Exception {
		Conta conta = findContaById(contaId);
		return conta.getVendas();
	}
	
	public Conta findContaById(Long contaId) throws Exception {
		Optional<Conta> contaOptional = contaRepository.findById(contaId);
		if (!contaOptional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Conta inexistente");
		}
		return contaOptional.get();
	}
	
}
