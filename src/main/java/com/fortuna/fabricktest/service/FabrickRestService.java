package com.fortuna.fabricktest.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.fortuna.fabricktest.exception.FabrickRestException;

/*
 * 
 * Classe di utilità con metodi rest di base per chiamare gli endpoint fabrick
 */
public class FabrickRestService {

	@Value("${fabrick.authSchema}")
	private String authSchema;
	
	@Value("${fabrick.apiKey}")
	private String apiKey;

	
	protected <T extends FabrickResponse<?>> ResponseEntity<T> get(URI uri, ParameterizedTypeReference<T> typeRef) {
		
		RestTemplate client = new RestTemplate();
		
		RequestEntity<Void> requestEntity = RequestEntity.get(uri)
				.header("Auth-Schema", authSchema)
				.header("Api-Key", apiKey)
				.build();
		
		ResponseEntity<T> resEntity = null;
		try {
			resEntity = client.exchange(requestEntity, typeRef);
		} catch (RestClientResponseException e) {
			T body = e.getResponseBodyAs(typeRef);	
			FabrickRestException restExc = new FabrickRestException(e.getStatusCode(), body.getErrors(), e);
			throw restExc;
		}
		
		
		return resEntity;
	}
	
	protected <T,E extends FabrickResponse<?>> ResponseEntity<E> post(URI uri, T bodyRequest, ParameterizedTypeReference<E> typeRef) {
		
		RestTemplate client = new RestTemplate();
		
		RequestEntity<T> requestEntity = RequestEntity.post(uri)
				.header("Auth-Schema", authSchema)
				.header("Api-Key", apiKey)
				.header("X-Time-Zone", "Europe/Rome")
				.body(bodyRequest);
		
		ResponseEntity<E> resEntity = null;
		try {
			resEntity = client.exchange(requestEntity, typeRef);
		} catch (RestClientResponseException e) {
			E bodyResponse = e.getResponseBodyAs(typeRef);	
			FabrickRestException restExc = new FabrickRestException(e.getStatusCode(), bodyResponse.getErrors(), e);
			throw restExc;
		}
		
		return resEntity;
	}
}