package com.funck.softexpert.desafio.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.funck.softexpert.desafio.event.RecursoCriadoEvent;
import com.funck.softexpert.desafio.model.Monitoramento;
import com.funck.softexpert.desafio.service.MonitoramentoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Monitoramentos", description = "Acesso aos recursos de monitoramentos")
@RestController
@RequestMapping("/monitoramentos")
public class MonitoramentoResource {

	@Autowired
	private MonitoramentoService monitoramentoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@ApiOperation("Retorna uma lista com todos os monitoramentos cadastrados")
	@GetMapping
	public List<Monitoramento> getAllMonitoramentos() {
		return monitoramentoService.findAll();
	}
	
	@ApiOperation("Salva um novo monitoramento")
	@PostMapping
	public ResponseEntity<Monitoramento> save(@RequestBody Monitoramento monitoramento, HttpServletResponse response) {
		Monitoramento monitoramentoSalvo = monitoramentoService.save(monitoramento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, monitoramentoSalvo.getId()));
		return ResponseEntity.ok(monitoramentoSalvo);
	}
	
	@ApiOperation("Retorna um monitoramento pelo id")
	@GetMapping("/{id}")
	public ResponseEntity<?> getMonitoramento(@PathVariable Long id) throws Exception {
		Optional<Monitoramento> monitoramento = monitoramentoService.findById(id);
		return monitoramento.isPresent() ? ResponseEntity.ok(monitoramento.get()) : ResponseEntity.noContent().build();
	}
		
	@ApiOperation("Atualiza um monitoramento pelo id")
	@PutMapping("/{id}")
	public Monitoramento update(@PathVariable Long id, @RequestBody Monitoramento monitoramento) throws Exception {
		return monitoramentoService.update(monitoramento, id);
	}
	
	@ApiOperation("Exclui um monitoramento pelo id")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		monitoramentoService.delete(id);
	}
	
}
