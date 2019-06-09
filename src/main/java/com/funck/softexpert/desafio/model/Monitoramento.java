package com.funck.softexpert.desafio.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity(name = "tb_monitoramento")
public class Monitoramento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "conta_id", nullable = false)
	private Conta conta;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "empresa_id", nullable = false)
	private Empresa empresa;
	
	@NotNull
	@Column(name = "preco_compra", precision = 15, scale = 2, nullable = false)
	private Double precoCompra;
	
	@NotNull
	@Column(name = "preco_venda", precision = 15, scale = 2, nullable = false)
	private Double precoVenda;
	
	/**
	 * Construtor padrão
	 * 
	 * @author funck
	 */
	public Monitoramento() {
		this.precoCompra = 0D;
		this.precoVenda = 0D;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Conta getConta() {
		return conta;
	}
	
	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Double getPrecoCompra() {
		return precoCompra;
	}

	public void setPrecoCompra(Double precoCompra) {
		this.precoCompra = precoCompra;
	}

	public Double getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(Double precoVenda) {
		this.precoVenda = precoVenda;
	}
	
	public boolean isComprarAcao() {
		return (precoCompra >= empresa.getPrecoAcao()) && (conta.getSaldoAtual() >= empresa.getPrecoAcao());
	}

	public boolean isVenderAcao() {
		if (precoVenda > empresa.getPrecoAcao()) {
			return false;
		}
		
		boolean temAcoesVender = conta.getAcoesEmpresa()
				.stream()
				.anyMatch(acaoEmpresa -> acaoEmpresa.getEmpresa().equals(empresa) && acaoEmpresa.getQuantidadeAcoes() > 0);
		
		return temAcoesVender;
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
		Monitoramento other = (Monitoramento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
