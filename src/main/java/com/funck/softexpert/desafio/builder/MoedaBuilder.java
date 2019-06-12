package com.funck.softexpert.desafio.builder;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class MoedaBuilder {

	public static String buildReal(Double valor) {
		BigDecimal valorMoeda = new BigDecimal (valor);  
		NumberFormat formatador = NumberFormat.getCurrencyInstance();  
		return formatador.format (valorMoeda);
	}
	
}
