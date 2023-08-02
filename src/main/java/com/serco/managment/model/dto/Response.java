package com.serco.managment.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {
	@JsonProperty("message")
	private String message;

	String getMessage() {
		return message;
	}

	private Response(ResponseBuilder builder) {
		this.message = builder.message;
	}
	
	public static class ResponseBuilder{
		private String message;
		
		public ResponseBuilder(String message) {
			this.message = message;
		}
		public Response build() {
			return new Response(this);
		}
		
	}
}
