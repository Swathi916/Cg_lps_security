package com.capgemini.lps.handler;

	import java.io.IOException;
	import java.io.PrintWriter;

	import javax.servlet.ServletException;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;

	import org.springframework.http.MediaType;
	import org.springframework.security.core.Authentication;
	import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
	import org.springframework.stereotype.Component;

import com.capgemini.lps.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
	@Component
	public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
			@SuppressWarnings("rawtypes")
			Response respons = new Response();
			respons.setMessage("Login Successfull");
			
			response.setStatus(200);
			response.setContentType(MediaType.APPLICATION_STREAM_JSON_VALUE);
		    ObjectMapper mapper = new ObjectMapper();
		    String json = mapper.writeValueAsString(respons);
		    PrintWriter out = response.getWriter();
		    out.write(json);
		}

	}



