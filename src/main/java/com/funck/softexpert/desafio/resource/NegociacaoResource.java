package com.funck.softexpert.desafio.resource;

import java.util.List;
import java.util.Optional;

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
import com.funck.softexpert.desafio.model.NegociacaoAcao;
import com.funck.softexpert.desafio.service.NegociacaoAcaoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Negociações", description = "Acesso aos recursos de negociações")
@RestController
@RequestMapping("/negociacoes")
public class NegociacaoResource {

	@Autowired
	private NegociacaoAcaoService negociacaoAcaoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@ApiOperation("Retorna uma lista com todas negociações cadastradas")
	@GetMapping
	public List<NegociacaoAcao> findAllNegociacoes() {
		return negociacaoAcaoService.findAllNegociacoes();
	}
	
	@ApiOperation("Salva uma nova negociação")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<NegociacaoAcao> save(@Valid @RequestBody NegociacaoAcao negociacao, HttpServletResponse response) {
		NegociacaoAcao negociacaoSalvo = negociacaoAcaoService.save(negociacao);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, negociacaoSalvo.getId()));
		return ResponseEntity.ok(negociacaoSalvo);
	}
	
	@ApiOperation("Retorna uma negociação pelo id")
	@GetMapping("/{id}")
	public ResponseEntity<?> findNegociacaoById(@PathVariable Long id) {
		Optional<NegociacaoAcao> negociacaoAcao = negociacaoAcaoService.findNegociacaoById(id);
		return negociacaoAcao.isPresent() ? ResponseEntity.ok(negociacaoAcao.get()) : ResponseEntity.noContent().build();
	}
	
}
