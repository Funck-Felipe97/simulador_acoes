package com.funck.softexpert.desafio.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "tb_conta")
public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "email_notificacao", nullable = false)
	private String emailNotificacao;
	
	@NotNull
	@Column(name = "saldo_inicial", nullable = false, precision = 15, scale = 2)
	private Double saldoInicial;
	
	@JsonIgnore
	@XmlTransient
	@OneToMany(mappedBy = "conta", fetch = FetchType.EAGER)
	private Set<Monitoramento> monitoramentos;
	
	@JsonIgnore
	@XmlTransient
	@OneToMany(mappedBy = "conta", fetch = FetchType.EAGER)
	private Set<NegociacaoAcao> negociacoes;
	
	@JsonIgnore
	@XmlTransient
	@OneToMany(mappedBy = "conta", fetch = FetchType.EAGER)
	private Set<AcaoEmpresa> acoesEmpresa;

	/**
	 * Construtor padrão
	 * 
	 * @author funck
	 */
	public Conta() {
		this.saldoInicial = 0D;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmailNotificacao() {
		return emailNotificacao;
	}

	public void setEmailNotificacao(String emailNotificacao) {
		this.emailNotificacao = emailNotificacao;
	}

	public Double getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(Double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}
	
	public Set<Monitoramento> getMonitoramentos() {
		if (monitoramentos == null) {
			monitoramentos = new HashSet<>();
		}
		return monitoramentos;
	}
	
	public void setMonitoramentos(Set<Monitoramento> monitoramentos) {
		this.monitoramentos = monitoramentos;
	}
	
	public Set<NegociacaoAcao> getNegociacoes() {
		if (negociacoes == null) {
			negociacoes = new HashSet<>();
		}
		return negociacoes;
	}
	
	public void setNegociacoes(Set<NegociacaoAcao> negociacoes) {
		this.negociacoes = negociacoes;
	}
	
	public Set<AcaoEmpresa> getAcoesEmpresa() {
		if (acoesEmpresa == null) {
			acoesEmpresa = new HashSet<>();
		}
		return acoesEmpresa;
	}
	
	public void setAcoesEmpresa(Set<AcaoEmpresa> acoesEmpresa) {
		this.acoesEmpresa = acoesEmpresa;
	}
	
	@JsonIgnore
	@XmlTransient
	public List<NegociacaoAcao> getCompras() {
		return getNegociacoes()
				.stream()
				.filter(negociacao -> TipoNegociacaoAcao.COMPRA.equals(negociacao.getTipoNegociacaoAcao()))
				.collect(Collectors.toList());
	}

	@JsonIgnore
	@XmlTransient
	public List<NegociacaoAcao> getVendas() {
		return getNegociacoes()
				.stream()
				.filter(negociacao -> TipoNegociacaoAcao.VENDA.equals(negociacao.getTipoNegociacaoAcao()))
				.collect(Collectors.toList());
	}
	
	public Double getValorVendas() {
		return getVendas().stream().mapToDouble(NegociacaoAcao::getValorNegociacao).sum();
	}
	
	public Double getValorCompras() {
		return getCompras().stream().mapToDouble(NegociacaoAcao::getValorNegociacao).sum();
	}
	
	public Double getSaldoAtual() {
		return getSaldoInicial() + getValorVendas() - getValorCompras();
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
		Conta other = (Conta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Conta: " + getId() + ", email: " + getEmailNotificacao() + ", saldo atual: " + getSaldoAtual();
	}
	
}
