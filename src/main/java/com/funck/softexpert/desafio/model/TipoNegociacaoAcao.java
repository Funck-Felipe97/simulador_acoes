package com.funck.softexpert.desafio.model;

public enum TipoNegociacaoAcao {

	VENDA("venda de ações"),
	COMPRA("compra de ações");
	
	private final String descricao;
	
	/**
	 * Construtor padrão
	 * 
	 * @param descricao Descrição do tipo de negociação
	 * @author funck
	 */
	private TipoNegociacaoAcao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	@Override
	public String toString() {
		return getDescricao();
	}
	
}
