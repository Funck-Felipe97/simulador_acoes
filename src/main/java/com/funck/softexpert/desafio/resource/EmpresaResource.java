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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.funck.softexpert.desafio.event.RecursoCriadoEvent;
import com.funck.softexpert.desafio.model.Empresa;
import com.funck.softexpert.desafio.service.EmpresaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Empresas", description = "Acesso aos recursos de empresas")
@RestController
@RequestMapping("/empresas")
public class EmpresaResource {

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@ApiOperation("Retorna uma lista com todas as empresas cadastradas")
	@GetMapping
	public List<Empresa> findAll() {
		return empresaService.findAll();
	}
	
	@ApiOperation("Salva uma nova empresa")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Empresa> save(@Valid @RequestBody Empresa empresa, HttpServletResponse response) {
		Empresa empresaSalva = empresaService.save(empresa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, empresaSalva.getId()));
		return ResponseEntity.ok(empresaSalva);
	}

	@ApiOperation("Retorna uma empresa pelo id")
	@GetMapping("/{id}")
	public ResponseEntity<?> findContaById(@PathVariable Long id) {
		Optional<Empresa> empresa = empresaService.findById(id);
		return empresa.isPresent() ? ResponseEntity.ok(empresa.get()) : ResponseEntity.noContent().build();
	}

	@ApiOperation("Atualiza uma empresa pelo id")
	@PutMapping("/{id}")
	public Empresa update(@PathVariable Long id, @RequestBody Empresa empresa) throws Exception {
		return empresaService.update(empresa, id);
	}
	
}
