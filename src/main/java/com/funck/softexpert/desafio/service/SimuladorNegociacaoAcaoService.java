package com.funck.softexpert.desafio.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funck.softexpert.desafio.model.Empresa;

@Service
public class SimuladorNegociacaoAcaoService {

	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private ContaService contaService;

	public void iniciarSimulador() {
		new Thread(() -> {
			Optional<Empresa> empresaOptional = empresaService.findById(1L);
			if (!empresaOptional.isPresent()) {
				return;
			}
			Empresa empresa = empresaOptional.get();
			
			for (int i = 0; i < 100; ++i) {
				empresa.setPrecoAcao(getNovoPrecoAcao());
				try {
					empresaService.update(empresa, empresa.getId());
					System.out.println("--- Empresa atualizada ---");
					Thread.sleep(5000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			gerarRelatorioContas();
		}).start();
	}

	private void gerarRelatorioContas() {
		try {
			
			StringBuilder extratoTodasContas = new StringBuilder();
			contaService.findAll()
					.forEach(conta -> extratoTodasContas.append("\n\n" + conta.getExtratoAsString() + "\n\n"));
			
			FileOutputStream fileOutputStream;
			PrintStream printStream;
			fileOutputStream = new FileOutputStream(new File("relatorio_contas.txt").getAbsoluteFile(), true);
			printStream = new PrintStream(fileOutputStream);
			printStream.println(extratoTodasContas);
			printStream.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Double getNovoPrecoAcao() {
		return (10 + (1 * Math.random()));
	}

}
