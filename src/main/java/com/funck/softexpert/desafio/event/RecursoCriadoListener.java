package com.funck.softexpert.desafio.event;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Listener de eventos para a classe RecursoCriadEvent
 * 
 * @author funck
 */
@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

	/**
	 * Método para adicionar a uri do novo recurso no header quando um recurso for criado
	 * 
	 * @funck
	 */
	@Override
	public void onApplicationEvent(RecursoCriadoEvent event) {
		HttpServletResponse response = event.getResponse();
		Long id = event.getId();

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(id).toUri();

		response.setHeader("Location", uri.toASCIIString());
	}

}
