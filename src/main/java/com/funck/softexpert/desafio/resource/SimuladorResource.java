package com.funck.softexpert.desafio.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.funck.softexpert.desafio.service.SimuladorNegociacaoAcaoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Simulador", description = "Acesso ao simulador para geração de preços de ações")
@RestController
@RequestMapping("/simulador")
public class SimuladorResource {

	@Autowired
	private SimuladorNegociacaoAcaoService simuladorNegociacaoAcaoService;
	
	@ApiOperation("Inicia uma nova simulação")
	@GetMapping
	public String initSimulador() {
		simuladorNegociacaoAcaoService.iniciarSimulador();
		return "Simulador iniciado";
	}
	
}
