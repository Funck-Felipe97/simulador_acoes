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
import com.funck.softexpert.desafio.model.Empresa;
import com.funck.softexpert.desafio.model.Monitoramento;
import com.funck.softexpert.desafio.repository.ContaRepository;
import com.funck.softexpert.desafio.repository.EmpresaRepository;
import com.funck.softexpert.desafio.repository.MonitoramentoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MonitoramentoTest {

	@Autowired
	private MonitoramentoRepository monitoramentoRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Test
	public void createShouldPersistMonitoramento() {
		Conta conta = new Conta();
		conta.setEmailNotificacao("teste.conta@gmail.com");
		conta.setSaldoInicial(100D);
		conta = contaRepository.save(conta);
		
		Empresa empresa = new Empresa();
		empresa.setNome("Softexpert");
		empresa.setPrecoAcao(15.00D);
		empresa = empresaRepository.save(empresa);
		
		Monitoramento monitoramento = new Monitoramento();
		monitoramento.setConta(conta);
		monitoramento.setEmpresa(empresa);
		monitoramento.setPrecoCompra(10.00D);
		monitoramento.setPrecoVenda(20.00D);
		
		monitoramento = monitoramentoRepository.save(monitoramento);
		
		assertNotNull(monitoramento.getId());
		assertEquals(monitoramento.getConta(), conta);
		assertEquals(monitoramento.getEmpresa(), monitoramento.getEmpresa());
		assertEquals(monitoramento.getPrecoCompra(), 10.0D, 0D);
		assertEquals(monitoramento.getPrecoVenda(), 20.0D, 0D);
	}
	
	@Test
	public void deleteShouldRemoveMonitoramento() {
		Conta conta = new Conta();
		conta.setEmailNotificacao("teste.conta@gmail.com");
		conta.setSaldoInicial(100D);
		conta = contaRepository.save(conta);
		
		Empresa empresa = new Empresa();
		empresa.setNome("Softexpert");
		empresa.setPrecoAcao(15.00D);
		empresa = empresaRepository.save(empresa);
		
		Monitoramento monitoramento = new Monitoramento();
		monitoramento.setConta(conta);
		monitoramento.setEmpresa(empresa);
		monitoramento.setPrecoCompra(10.00D);
		monitoramento.setPrecoVenda(20.00D);
		
		monitoramento = monitoramentoRepository.save(monitoramento);
		monitoramentoRepository.delete(monitoramento);
		Optional<Monitoramento> monitoramentoOptional = monitoramentoRepository.findById(monitoramento.getId());
		
		assertFalse(monitoramentoOptional.isPresent());
	}
	
	@Test
	public void updateShouldChangeAndPersistMonitoramento() {
		Conta conta = new Conta();
		conta.setEmailNotificacao("teste.conta@gmail.com");
		conta.setSaldoInicial(100D);
		conta = contaRepository.save(conta);
		
		Empresa empresa = new Empresa();
		empresa.setNome("Softexpert");
		empresa.setPrecoAcao(15.00D);
		empresa = empresaRepository.save(empresa);
		
		Monitoramento monitoramento = new Monitoramento();
		monitoramento.setConta(conta);
		monitoramento.setEmpresa(empresa);
		monitoramento.setPrecoCompra(10.00D);
		monitoramento.setPrecoVenda(20.00D);
		monitoramentoRepository.saveAndFlush(monitoramento);
		
		monitoramento.setPrecoCompra(11.00D);
		monitoramento.setPrecoVenda(19.00D);
		monitoramentoRepository.save(monitoramento);
		monitoramento = monitoramentoRepository.findById(monitoramento.getId()).get();
		
		assertEquals(monitoramento.getPrecoCompra(), 11.0D, 0D);
		assertEquals(monitoramento.getPrecoVenda(), 19.0D, 0D);
	}
	
}
