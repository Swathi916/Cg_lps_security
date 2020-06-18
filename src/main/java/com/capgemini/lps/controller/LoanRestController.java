package com.capgemini.lps.controller;


import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.capgemini.lps.entity.Loan;

import com.capgemini.lps.response.Response;
import com.capgemini.lps.service.LoanService;


@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api1")
public class LoanRestController {

	@Autowired
	private RestTemplate restTemplate;
	
	
	public LoanRestController(LoanService theLoanService) {
	}
	
//not used	
	@SuppressWarnings("unchecked")
	@GetMapping("/getLoans")
	public List<Loan> findAll1() {
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity <String> entity = new HttpEntity<String>(headers);
	      
	      return restTemplate.exchange("http://localhost:8084/api1/getLoans", HttpMethod.GET, entity, List.class).getBody();
	}

	
	@SuppressWarnings("unchecked")
	@GetMapping("/getLoansByNo/{loanNo}")
	public Response<Loan> getLoans(@PathVariable int loanNo) { 
		
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity <String> entity = new HttpEntity<String>(headers);
	      
	      return restTemplate.exchange("http://localhost:8083/api1/getLoansByNo/"+loanNo, HttpMethod.GET, entity, Response.class).getBody();
	 
		
	}
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/addLoans")
	public Response<Loan> addLoan(@Valid @RequestBody Loan theLoan) {
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	      HttpEntity<Loan> entity = new HttpEntity<Loan>(theLoan,headers);
	      
	      return restTemplate.exchange("http://localhost:8083/api1/addLoans", HttpMethod.POST, entity, Response.class).getBody();
	
	}
	
	
	
	@SuppressWarnings("unchecked")
	@PutMapping("/updateLoans")
	public Response<Loan> updateProduct(@Valid @RequestBody Loan theProduct) {
		
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity<Loan> entity = new HttpEntity<Loan>(theProduct,headers);
	      
	      return restTemplate.exchange(
	         "http://localhost:8083/api1/updateLoans", HttpMethod.PUT, entity,Response.class).getBody();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@DeleteMapping("/deleteLoans/{loanNo}")
	public Response<Loan> deleteProduct(@PathVariable int loanNo) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<Loan> entity = new HttpEntity<Loan>(headers);

		return restTemplate.exchange("http://localhost:8083/api1/deleteLoans/" + loanNo , HttpMethod.DELETE, entity, Response.class)
				.getBody();

		

	}

	@GetMapping("/getLoansByPage/{pageNo}/{itemsPerPage}")
	public Object getLoans(@PathVariable int pageNo ,@PathVariable int itemsPerPage){ 
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		return restTemplate.exchange("http://localhost:8084/api/getLoansByPage/" + pageNo + "/" + itemsPerPage, HttpMethod.GET, entity,Object.class).getBody();
	}
	
	
	@GetMapping("/getSortedLoans/{pageNo}/{itemsPerPage}/{fieldName}")
	public Object getSortLoans(@PathVariable int pageNo,@PathVariable int itemsPerPage,@PathVariable String fieldName) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		return restTemplate.exchange("http://localhost:8084/api/getSortedLoans/" + pageNo + "/" + itemsPerPage + "/"+ fieldName, HttpMethod.GET, entity,Object.class).getBody();
	}


}














