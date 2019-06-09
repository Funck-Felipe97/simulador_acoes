package com.funck.softexpert.desafio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender emailSender;
	
	@Async
	public void enviarEmail(SimpleMailMessage mensagemEmail) {
        try {
            emailSender.send(mensagemEmail);
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
}
