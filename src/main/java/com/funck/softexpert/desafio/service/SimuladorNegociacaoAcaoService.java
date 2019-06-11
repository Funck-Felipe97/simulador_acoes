package com.funck.softexpert.desafio.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funck.softexpert.desafio.model.Conta;
import com.funck.softexpert.desafio.model.Empresa;

@Service
public class SimuladorNegociacaoAcaoService {

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private ContaService contaService;

	/**
	 * Método responsável por atualizar o valor de compra de ações de todas as
	 * empresas cadastradas em um intervalor de 5 segundos por 100 iterações
	 * 
	 * @author funck
	 */
	public void iniciarSimulador() {
		new Thread(() -> {
			List<Empresa> empresas = empresaService.findAll();
			if (empresas == null || empresas.isEmpty()) {
				return;
			}

			for (int i = 0; i < 100; ++i) {
				for (Empresa empresa : empresas) {
					empresa.setPrecoAcao(getNovoPrecoAcao());
					try {
						empresaService.update(empresa, empresa.getId());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				System.out.printf("--- Atualização (%d) ---\n", i);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			gerarRelatorioContas();

		}).start();
	}

	private void gerarRelatorioContas() {
		try {
			FileOutputStream fileOutputStream;
			PrintStream printStream;
			fileOutputStream = new FileOutputStream(new File("relatorio_contas.txt").getAbsoluteFile(), true);
			printStream = new PrintStream(fileOutputStream);

			for (Conta conta : contaService.findAll()) {
				printStream.println(conta);
				printStream.println("--- Vendas realizadas ---");
				conta.getVendas().forEach(venda -> printStream.println(venda));
				printStream.println("--- Compras realizadas ---");
				conta.getCompras().forEach(compra -> printStream.println(compra));
				printStream.println("");
				printStream.println("");
			}

			printStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Double getNovoPrecoAcao() {
		return (10 + (1 * Math.random()));
	}

}
