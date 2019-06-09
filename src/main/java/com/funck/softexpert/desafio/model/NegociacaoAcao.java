package com.funck.softexpert.desafio.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.StringJoiner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity(name = "tb_negociacao_acao")
public class NegociacaoAcao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "data_negociacao", nullable = false)
	private LocalDateTime dataNegociacao;
	
	@NotNull
	@Column(name = "quantidade_acoes", nullable = false, precision = 15, scale = 2)
	private Double quantidadeAcoes;
	
	@NotNull
	@Column(name = "valor_acao", nullable = false, precision = 15, scale = 2)
	private Double valorAcao;
	
	@NotNull
	@Column(name = "valor_negociacao", nullable = false, precision = 15, scale = 2)
	private Double valorNegociacao;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "conta_id")
	private Conta conta;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_negociacao_acao")
	private TipoNegociacaoAcao tipoNegociacaoAcao;
	
	/**
	 * Construtor padrão
	 * 
	 * @author funck
	 */
	public NegociacaoAcao() {
		this.valorAcao = 0D;
		this.quantidadeAcoes = 0D;
		this.valorNegociacao = 0D;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataNegociacao() {
		return dataNegociacao;
	}

	public void setDataNegociacao(LocalDateTime dataNegociacao) {
		this.dataNegociacao = dataNegociacao;
	}

	public Double getQuantidadeAcoes() {
		return quantidadeAcoes;
	}

	public void setQuantidadeAcoes(Double quantidadeAcoes) {
		this.quantidadeAcoes = quantidadeAcoes;
	}

	public Double getValorAcao() {
		return valorAcao;
	}

	public void setValorAcao(Double valorAcao) {
		this.valorAcao = valorAcao;
	}

	public Double getValorNegociacao() {
		return valorNegociacao;
	}

	public void setValorNegociacao(Double valorNegociacao) {
		this.valorNegociacao = valorNegociacao;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	public TipoNegociacaoAcao getTipoNegociacaoAcao() {
		return tipoNegociacaoAcao;
	}
	
	public void setTipoNegociacaoAcao(TipoNegociacaoAcao tipoNegociacaoAcao) {
		this.tipoNegociacaoAcao = tipoNegociacaoAcao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NegociacaoAcao other = (NegociacaoAcao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringJoiner negociacaoToString = new StringJoiner(", ");
		
		negociacaoToString
			.add(tipoNegociacaoAcao.getDescricao())
			.add("Empresa: " + getEmpresa().getNome())
			.add("Quantidade de ações: " + getQuantidadeAcoes())
			.add("Valor da ação: " + getValorAcao())
			.add("Valor da negociação: " + getValorNegociacao())
			.add("Data: " + getDataNegociacao());
		
		return negociacaoToString.toString();
	}
	
}
