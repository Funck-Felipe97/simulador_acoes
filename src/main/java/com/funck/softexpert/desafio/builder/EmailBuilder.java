package com.funck.softexpert.desafio.builder;

import org.springframework.mail.SimpleMailMessage;

import com.funck.softexpert.desafio.model.NegociacaoAcao;

public class EmailBuilder {

	public static SimpleMailMessage buildEmailNegociacao(NegociacaoAcao negociacaoAcao) {
		StringBuilder conteudoMensagem = new StringBuilder();
		
		conteudoMensagem
				.append("Você acabou de realizar a" + negociacaoAcao.getTipoNegociacaoAcao().getDescricao())
				.append("\nEmpresa: " + negociacaoAcao.getEmpresa().getNome())
				.append("\nValor da ação: " + negociacaoAcao.getValorAcao())
				.append("\nQuantidade de ações: " + negociacaoAcao.getQuantidadeAcoes())
				.append("\nValor da negociação: " + negociacaoAcao.getValorNegociacao());

		SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject("Nova negociação de ações");
		email.setText(conteudoMensagem.toString());
		email.setTo(negociacaoAcao.getConta().getEmailNotificacao());
		email.setFrom("simulador.acoes.java@gmail.com");
		return email;
	}

}
