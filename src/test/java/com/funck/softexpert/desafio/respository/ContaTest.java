package com.funck.softexpert.desafio.respository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.funck.softexpert.desafio.model.Conta;
import com.funck.softexpert.desafio.repository.ContaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContaTest {

	@Autowired
	private ContaRepository contaRepository;
	
	@Test
	public void createShouldPersistConta() {
		Conta conta = new Conta();
		conta.setEmailNotificacao("teste.conta@gmail.com");
		conta.setSaldoInicial(100D);
		conta = contaRepository.save(conta);
		
		assertNotNull(conta.getId());
		assertEquals(conta.getEmailNotificacao(), "teste.conta@gmail.com");
		assertEquals(conta.getSaldoInicial(), conta.getSaldoAtual(), 0D);
	}
	
	@Test
	public void deleteShouldRemoveConta() {
		Conta conta = new Conta();
		conta.setEmailNotificacao("teste.conta@gmail.com");
		conta.setSaldoInicial(100D);
		conta = contaRepository.save(conta);
		contaRepository.delete(conta);
		Optional<Conta> contaOptional = contaRepository.findById(conta.getId());
		
		assertFalse(contaOptional.isPresent());
	}
	
	@Test
	public void updateShouldChangeAndPersistEmpresa() { 
		Conta conta = new Conta();
		conta.setEmailNotificacao("teste.conta@gmail.com");
		conta.setSaldoInicial(100D);
		conta = contaRepository.save(conta);
		
		conta.setEmailNotificacao("novoteste.conta@gmail.com");
		conta.setSaldoInicial(200D);
		contaRepository.save(conta);
		conta = contaRepository.findById(conta.getId()).get();
		
		assertEquals(conta.getEmailNotificacao(), "novoteste.conta@gmail.com");
		assertEquals(conta.getSaldoInicial(), 200D, 0D);
	}
	
}
