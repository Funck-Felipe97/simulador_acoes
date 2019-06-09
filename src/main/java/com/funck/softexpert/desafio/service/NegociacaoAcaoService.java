package com.funck.softexpert.desafio.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.funck.softexpert.desafio.builder.EmailBuilder;
import com.funck.softexpert.desafio.model.AcaoEmpresa;
import com.funck.softexpert.desafio.model.Conta;
import com.funck.softexpert.desafio.model.Empresa;
import com.funck.softexpert.desafio.model.Monitoramento;
import com.funck.softexpert.desafio.model.NegociacaoAcao;
import com.funck.softexpert.desafio.model.TipoNegociacaoAcao;
import com.funck.softexpert.desafio.repository.AcaoEmpresaRepository;
import com.funck.softexpert.desafio.repository.NegociacaoAcaoRepository;

@Service
public class NegociacaoAcaoService {

	@Autowired
	private NegociacaoAcaoRepository negociacaoAcaoRepository;

	@Autowired
	private AcaoEmpresaRepository acaoEmpresaRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private MonitoramentoService monitoramentoService;

	public Optional<NegociacaoAcao> findNegociacaoById(Long id) {
		return negociacaoAcaoRepository.findById(id);
	}

	public List<NegociacaoAcao> findAllNegociacoes() {
		return negociacaoAcaoRepository.findAll();
	}

	public NegociacaoAcao save(NegociacaoAcao negociacao) {
		return negociacaoAcaoRepository.save(negociacao);
	}

	@Async
	public void saveNegociacoesByEmpresa(Empresa empresa) {
		Set<Monitoramento> monitoramentos = monitoramentoService.findByEmpresa(empresa);
		for (Monitoramento monitoramento : monitoramentos) {
			if (monitoramento.isComprarAcao()) {
				Empresa emp = new Empresa();
				BeanUtils.copyProperties(empresa, emp);
				realizarCompra(emp, monitoramento.getConta());
				return;
			}
			if (monitoramento.isVenderAcao()) {
				realizarVenda(empresa, monitoramento.getConta());
			}
		}
	}

	@Transactional
	private void realizarVenda(Empresa empresa, Conta conta) {
		List<AcaoEmpresa> acoes = conta.getAcoesEmpresa().stream()
				.filter(acoesEmpresa -> acoesEmpresa.getEmpresa().equals(empresa)).collect(Collectors.toList());

		Double quantidadeTotalAcoes = acoes.stream().mapToDouble(AcaoEmpresa::getQuantidadeAcoes).sum();
		Double valorAcao = empresa.getPrecoAcao();
		Double valorTotalAcoes = quantidadeTotalAcoes * valorAcao;

		NegociacaoAcao negociacaoAcao = new NegociacaoAcao();
		negociacaoAcao.setConta(conta);
		negociacaoAcao.setEmpresa(empresa);
		negociacaoAcao.setTipoNegociacaoAcao(TipoNegociacaoAcao.VENDA);
		negociacaoAcao.setDataNegociacao(LocalDateTime.now());
		negociacaoAcao.setQuantidadeAcoes(quantidadeTotalAcoes);
		negociacaoAcao.setValorAcao(valorAcao);
		negociacaoAcao.setValorNegociacao(valorTotalAcoes);

		acoes.stream().forEach(acaoEmpresaRepository::delete);
		negociacaoAcaoRepository.saveAndFlush(negociacaoAcao);
		emailService.enviarEmail(EmailBuilder.buildEmailNegociacao(negociacaoAcao));
	}

	@Transactional
	private void realizarCompra(Empresa empresa, Conta conta) {

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
		negociacaoAcaoRepository.save(negociacaoAcao);
		emailService.enviarEmail(EmailBuilder.buildEmailNegociacao(negociacaoAcao));
	}

}
