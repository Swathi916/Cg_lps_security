package com.capgemini.lps.controller;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.capgemini.lps.entity.Applicant;
import com.capgemini.lps.response.Response;

@RestController
@RequestMapping("/api2")
@CrossOrigin(origins = "*")
public class ApplicantRestController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private RestTemplate restTemplate;

	//not used
	@SuppressWarnings("unchecked")
	@GetMapping("/loanApplication")
	public List<Applicant> findAllRequested() {
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity <String> entity = new HttpEntity<String>(headers);
	      
	      return restTemplate.exchange("http://localhost:8082/api2/loanApplication", HttpMethod.GET, entity, List.class).getBody();
	}

	@SuppressWarnings("unchecked")
	@PutMapping("/loanApplicationApprove/{appId}")
	public Response<Applicant> findAllApproved(@PathVariable Integer appId) {
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity<String> entity = new HttpEntity<String>(headers);
	      
	      return restTemplate.exchange(
	         "http://localhost:8082/api2/loanApplicationApprove/"+appId, HttpMethod.PUT, entity, Response.class).getBody();
	}

	@SuppressWarnings("unchecked")
	@PutMapping("/loanApplicationReject/{appId}")
	public Response<Applicant> findAllRejected(@PathVariable Integer appId) {
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity<String> entity = new HttpEntity<String>(headers);
	      
	      return restTemplate.exchange( "http://localhost:8082/api2/loanApplicationReject/"+appId, HttpMethod.PUT, entity, Response.class).getBody();
	  
	}

	//
//	@GetMapping("/loanApplication/{loanapplication}")
//	public Applicant getById(@PathVariable int loanapplication) {
//
//		Applicant applicant = applicantService.getById(loanapplication);
//
//		if (applicant == null) {
//			throw new ApplicantNotFoundException("id not found " + loanapplication);
//		}
//		return applicant;
//	}

	@SuppressWarnings("unchecked")
	@PostMapping("/AddLaoanApplication")
	public Response<Applicant> saveApplicant(@Valid @RequestBody Applicant applicant) {
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	      HttpEntity<Applicant> entity = new HttpEntity<Applicant>(applicant,headers);
	      
	      return restTemplate.exchange("http://localhost:8082/api2/AddLoanApplication", HttpMethod.POST, entity, Response.class).getBody();
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/makeloan/{email}")
	public Response<Applicant> makeLoan(@PathVariable String email, @Valid @RequestBody Applicant applicant) {
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	      HttpEntity<Applicant> entity = new HttpEntity<Applicant>(applicant,headers);
	      
	      return restTemplate.exchange("http://localhost:8084/api/makeloan/"+email, HttpMethod.POST, entity, Response.class).getBody();
	
	}

	//not used
	@PutMapping("/updateLoanApplication")
	public Applicant updateApplicant(@Valid @RequestBody Applicant applicant) {
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity<Applicant> entity = new HttpEntity<Applicant>(applicant,headers);
	      
	      return restTemplate.exchange("http://localhost:8082/api2/updateLoanApplication", HttpMethod.PUT, entity,Applicant.class).getBody();
	}
	
	

}