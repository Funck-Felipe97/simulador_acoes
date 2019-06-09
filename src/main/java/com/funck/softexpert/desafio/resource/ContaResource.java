package com.funck.softexpert.desafio.resource;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.funck.softexpert.desafio.event.RecursoCriadoEvent;
import com.funck.softexpert.desafio.model.Conta;
import com.funck.softexpert.desafio.model.Monitoramento;
import com.funck.softexpert.desafio.model.NegociacaoAcao;
import com.funck.softexpert.desafio.service.ContaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Contas", description = "Acesso aos recursos de contas")
@RestController
@RequestMapping("/contas")
public class ContaResource {

	@Autowired
	private ContaService contaService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@ApiOperation("Retorna uma lista com todas as contas cadastradas")
	@GetMapping
	public List<Conta> findAll() {
		return contaService.findAll();
	}

	@ApiOperation("Retorna uma conta pelo id")
	@GetMapping("/{conta}")
	public ResponseEntity<?> findContaById(@PathVariable Long conta) {
		Optional<Conta> contaOptional = contaService.findById(conta);
		return contaOptional.isPresent() ? ResponseEntity.ok(contaOptional.get()) : ResponseEntity.noContent().build();
	}
	
	@ApiOperation("Retorna todas negociações de uma conta pelo seu id")
	@GetMapping("/{conta}/negociacoes")
	public Set<NegociacaoAcao> findNegociacoes(@PathVariable Long conta) throws Exception {
		return contaService.findNegociacoesByConta(conta);
	}
	
	@ApiOperation("Retorna todas compras de uma conta pelo seu id")
	@GetMapping("/{conta}/negociacoes/compras")
	public List<NegociacaoAcao> findComprasAcoes(@PathVariable Long conta) throws Exception {
		return contaService.findComprasByConta(conta);
	}
	
	@ApiOperation("Retorna todas vendas de uma conta pelo seu id")
	@GetMapping("/{conta}/negociacoes/vendas")
	public List<NegociacaoAcao> findVendasAcoes(@PathVariable Long conta) throws Exception {
		return contaService.findVendasByConta(conta);
	}
	
	@ApiOperation("Retorna todos monitoramentos de uma conta pelo seu id")
	@GetMapping("/{conta}/monitoramentos")
	public Set<Monitoramento> findMonitoramentos(@PathVariable Long conta) throws Exception {
		return contaService.findMonitoramentosByConta(conta);
	}
	
	@ApiOperation("Salva uma nova conta")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Conta> save(@Valid @RequestBody Conta conta, HttpServletResponse response) {
		Conta contaSalva = contaService.save(conta);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, contaSalva.getId()));
		return ResponseEntity.ok(contaSalva);
	}

}
