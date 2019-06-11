package com.funck.softexpert.desafio.respository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.funck.softexpert.desafio.model.Empresa;
import com.funck.softexpert.desafio.repository.EmpresaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmpresaTest {

	@Autowired
	private EmpresaRepository empresaRepository;

	@Test
	public void createShouldPersistEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setNome("Softexpert");
		empresa.setPrecoAcao(15.00D);
		empresa = empresaRepository.save(empresa);
		
		assertNotNull(empresa.getId());
		assertEquals(empresa.getNome(), "Softexpert");
		assertEquals(empresa.getPrecoAcao(), 15.00D, 0);
	}
	
	@Test
	public void deleteShouldRemoveEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setNome("Softexpert");
		empresa.setPrecoAcao(15.00D);
		empresa = empresaRepository.save(empresa);
		empresaRepository.delete(empresa);
		Optional<Empresa> empresaOptional = empresaRepository.findById(empresa.getId());
		
		assertFalse(empresaOptional.isPresent());
	}
	
	@Test
	public void updateShouldChangeAndPersistEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setNome("Intel");
		empresa.setPrecoAcao(10.0D);
		empresaRepository.saveAndFlush(empresa);
		
		empresa.setNome("Google");
		empresa.setPrecoAcao(10.25D);
		empresaRepository.save(empresa);
		empresa = empresaRepository.findById(empresa.getId()).get();
		
		assertEquals(empresa.getNome(), "Google");
		assertEquals(empresa.getPrecoAcao(), 10.25D, 0);
	}
	
}
