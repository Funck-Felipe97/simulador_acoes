package com.funck.softexpert.desafio;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.funck.softexpert.desafio.service.SimuladorNegociacaoAcaoService;

@SpringBootApplication
public class DesafioApplication {

	@Autowired
	private SimuladorNegociacaoAcaoService simuladorNegociacaoAcaoService;
	
	public static void main(String[] args) {
		SpringApplication.run(DesafioApplication.class, args);
	}
	
	public void simularCompraVendaAcao() {
		simuladorNegociacaoAcaoService.iniciarSimulador();
	}

}
