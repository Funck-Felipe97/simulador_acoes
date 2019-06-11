package com.funck.softexpert.desafio.respository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.funck.softexpert.desafio.model.AcaoEmpresa;
import com.funck.softexpert.desafio.model.Conta;
import com.funck.softexpert.desafio.model.Empresa;
import com.funck.softexpert.desafio.model.NegociacaoAcao;
import com.funck.softexpert.desafio.model.TipoNegociacaoAcao;
import com.funck.softexpert.desafio.repository.AcaoEmpresaRepository;
import com.funck.softexpert.desafio.repository.ContaRepository;
import com.funck.softexpert.desafio.repository.EmpresaRepository;
import com.funck.softexpert.desafio.repository.NegociacaoAcaoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NegociacaoAcaoTest {

	@Autowired
	private NegociacaoAcaoRepository negociacaoAcaoRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private AcaoEmpresaRepository acaoEmpresaRepository;
	
	@Test
	public void createShouldPersistCompraAcao() {
		Conta conta = new Conta();
		conta.setEmailNotificacao("teste.conta.compra.conta@gmail.com");
		conta.setSaldoInicial(300D);
		conta = contaRepository.save(conta);
		
		Empresa empresa = new Empresa();
		empresa.setNome("Amazon");
		empresa.setPrecoAcao(10.00D);
		empresa = empresaRepository.save(empresa);
		
		Double valorAcao = empresa.getPrecoAcao();
		Double quantidadeTotalAcoes = conta.getSaldoAtual() / valorAcao;
		Double valorTotalAcoes = quantidadeTotalAcoes * valorAcao;

		NegociacaoAcao negociacaoAcao = new NegociacaoAcao();
		negociacaoAcao.setConta(conta);
		negociacaoAcao.setEmpresa(empresa);
		negociacaoAcao.setTipoNegociacaoAcao(TipoNegociacaoAcao.COMPRA);
		negociacaoAcao.setDataNegociacao(LocalDateTime.now());
		negociacaoAcao.setQuantidadeAcoes(quantidadeTotalAcoes);
		negociacaoAcao.setValorAcao(valorAcao);
		negociacaoAcao.setValorNegociacao(valorTotalAcoes);
		
		AcaoEmpresa acaoEmpresa = new AcaoEmpresa();
		acaoEmpresa.setConta(conta);
		acaoEmpresa.setEmpresa(empresa);
		acaoEmpresa.setQuantidadeAcoes(quantidadeTotalAcoes);

		acaoEmpresaRepository.saveAndFlush(acaoEmpresa);
		negociacaoAcaoRepository.saveAndFlush(negociacaoAcao);
		
		assertEquals(negociacaoAcao.getEmpresa(), empresa);
		assertEquals(negociacaoAcao.getConta(), conta);
		assertEquals(negociacaoAcao.getQuantidadeAcoes(), 30D, 0D);
		assertEquals(negociacaoAcao.getValorAcao(), 10D, 0D);
		assertEquals(negociacaoAcao.getValorNegociacao(), 300D, 0D);
		assertEquals(negociacaoAcao.getTipoNegociacaoAcao(), TipoNegociacaoAcao.COMPRA);
		assertNotNull(negociacaoAcao.getId());
	}
	
	@Test
	public void createShouldPersistVendaAcao() {
		final Conta conta = new Conta();
		conta.setEmailNotificacao("teste.conta.venda.conta@gmail.com");
		conta.setSaldoInicial(300D);
		contaRepository.saveAndFlush(conta);
		
		final Empresa empresa = new Empresa();
		empresa.setNome("Amazon");
		empresa.setPrecoAcao(10.00D);
		empresaRepository.saveAndFlush(empresa);
		
		Double valorAcao = empresa.getPrecoAcao();
		Double quantidadeTotalAcoes = conta.getSaldoAtual() / valorAcao;
		Double valorTotalAcoes = quantidadeTotalAcoes * valorAcao;

		NegociacaoAcao negociacaoAcao = new NegociacaoAcao();
		negociacaoAcao.setConta(conta);
		negociacaoAcao.setEmpresa(empresa);
		negociacaoAcao.setTipoNegociacaoAcao(TipoNegociacaoAcao.COMPRA);
		negociacaoAcao.setDataNegociacao(LocalDateTime.now());
		negociacaoAcao.setQuantidadeAcoes(quantidadeTotalAcoes);
		negociacaoAcao.setValorAcao(valorAcao);
		negociacaoAcao.setValorNegociacao(valorTotalAcoes);
		
		AcaoEmpresa acaoEmpresa = new AcaoEmpresa();
		acaoEmpresa.setConta(conta);
		acaoEmpresa.setEmpresa(empresa);
		acaoEmpresa.setQuantidadeAcoes(quantidadeTotalAcoes);

		acaoEmpresaRepository.saveAndFlush(acaoEmpresa);
		negociacaoAcaoRepository.saveAndFlush(negociacaoAcao);
		
		// Realizando venda	
		Set<AcaoEmpresa> acoes = conta.getAcoesEmpresa().stream()
				.filter(acoesEmpresa -> acoesEmpresa.getEmpresa().equals(empresa)).collect(Collectors.toSet());
		
		quantidadeTotalAcoes = acoes.stream().mapToDouble(AcaoEmpresa::getQuantidadeAcoes).sum();
		valorAcao = empresa.getPrecoAcao();
		valorTotalAcoes = quantidadeTotalAcoes * valorAcao;
		
		NegociacaoAcao negociacaoAcaoVenda = new NegociacaoAcao();
		negociacaoAcaoVenda.setConta(conta);
		negociacaoAcaoVenda.setEmpresa(empresa);
		negociacaoAcaoVenda.setDataNegociacao(LocalDateTime.now());
		negociacaoAcaoVenda.setTipoNegociacaoAcao(TipoNegociacaoAcao.VENDA);
		negociacaoAcaoVenda.setQuantidadeAcoes(quantidadeTotalAcoes);
		negociacaoAcaoVenda.setValorAcao(valorAcao);
		negociacaoAcaoVenda.setValorNegociacao(valorTotalAcoes);
		negociacaoAcaoRepository.saveAndFlush(negociacaoAcaoVenda);
		
		acoes.stream().forEach(acaoEmpresaRepository::delete);
		
		assertEquals(negociacaoAcaoVenda.getEmpresa(), empresa);
		assertEquals(negociacaoAcaoVenda.getConta(), conta);
		assertEquals(negociacaoAcaoVenda.getTipoNegociacaoAcao(), TipoNegociacaoAcao.VENDA);
		assertNotNull(negociacaoAcaoVenda.getId());
	}
	
}
