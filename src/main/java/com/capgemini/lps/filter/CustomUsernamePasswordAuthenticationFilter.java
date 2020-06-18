package com.capgemini.lps.filter;


	import java.io.BufferedReader;
	import java.io.IOException;

	import javax.servlet.http.HttpServletRequest;

	import org.springframework.http.MediaType;
	import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.capgemini.lps.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

	public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

		private User register;
		
		@Override
		protected String obtainUsername(HttpServletRequest request) {
			if(request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
				register = null;
				try {
					User reg = getRegisterInfo(request);
					return reg.getEmail();
				} catch (IOException e) {
					e.printStackTrace();
					return "";
				}
			}
			return super.obtainUsername(request);
		}
		
		@Override
		protected String obtainPassword(HttpServletRequest request) {
			if(request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
				
				try {
		         	User reg = getRegisterInfo(request);
					return reg.getPassword();
				} catch (Exception e) {
					e.printStackTrace();
					return "";
				}
			}
	          return super.obtainPassword(request);
		}
		
		private User getRegisterInfo(HttpServletRequest request) throws IOException {
			
			if(register == null) {
				ObjectMapper mapper = new ObjectMapper();
				String json = "";
				BufferedReader reader = request.getReader();
				while (reader.ready()) {
					json = json + reader.readLine();
				}
		        register = mapper.readValue(json, User.class);
			}
			return register;
		}
		
		
	}



