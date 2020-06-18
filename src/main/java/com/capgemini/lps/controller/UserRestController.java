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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.capgemini.lps.entity.Applicant;
import com.capgemini.lps.entity.JwtResponse;
import com.capgemini.lps.entity.User;
import com.capgemini.lps.exception.EmailNotFoundException;
import com.capgemini.lps.response.Response;
import com.capgemini.lps.security.UserDetailsImpl;
import com.capgemini.lps.service.JwtUtil;
import com.capgemini.lps.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserRestController {
	@Autowired
	private RestTemplate restTemplate;

	public UserRestController(UserService theUserService) {
	}

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) throws Exception {

		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		} catch (DisabledException de) {
			// we should use loggers here
			System.out.println("User is Disabled");
			throw new EmailNotFoundException("Email-Id does not exist !!!");

		} catch (BadCredentialsException bce) {
			// we should use loggers here
			throw new EmailNotFoundException("Invalid password");

		} // End of try catch

		final UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(user.getEmail());

		final String jwt = jwtUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUserId(), userDetails.getFullName(),
				userDetails.getEmail(), userDetails.getRole()));// doubt
	}// End of login()

	// not used
	@SuppressWarnings("unchecked")
	@GetMapping("/getUser")
	public List<User> findAll() {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		return restTemplate.exchange("http://localhost:8083/api1/getUser", HttpMethod.GET, entity, List.class)
				.getBody();
	}

	@SuppressWarnings("unchecked")
	@GetMapping("getUserById/{userId}")
	public Response<User> getClient(@PathVariable int userId) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		return restTemplate
				.exchange("http://localhost:8083/api1/getUserById/" + userId, HttpMethod.GET, entity, Response.class)
				.getBody();

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/addUser")
	public Response<User> addClient(@Valid @RequestBody User theUser) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<User> entity = new HttpEntity<User>(theUser, headers);

		return restTemplate.exchange("http://localhost:8083/api1/addUser", HttpMethod.POST, entity, Response.class)
				.getBody();
	}

	@SuppressWarnings("unchecked")
	@DeleteMapping("/deleteUser/{userId}")
	public Response<User> deleteClient(@PathVariable int userId) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<User> entity = new HttpEntity<User>(headers);

		return restTemplate
				.exchange("http://localhost:8083/api1/deleteUser/" + userId, HttpMethod.DELETE, entity, Response.class)
				.getBody();

	}

	@GetMapping("/getUserByPage/{pageNo}/{itemsPerPage}")
	public Object getClients(@PathVariable int pageNo, @PathVariable int itemsPerPage) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		return restTemplate.exchange("http://localhost:8083/api1/getUserByPage/" + pageNo + "/" + itemsPerPage,
				HttpMethod.GET, entity, Object.class).getBody();
	}

	@GetMapping("/sortUser/{pageNo}/{itemsPerPage}/{fieldName}")
	public Object getSortClients(@PathVariable int pageNo, @PathVariable int itemsPerPage,
			@PathVariable String fieldName) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		return restTemplate.exchange("http://localhost:8083/api1/sortUser/" + pageNo + "/"+ itemsPerPage +"/"+ fieldName ,
				HttpMethod.GET, entity, Object.class).getBody();
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/makeloan/{email}")
	public Response<Applicant> makeLoan(@PathVariable String email, @RequestBody Applicant applyloan) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<Applicant> entity = new HttpEntity<Applicant>(applyloan, headers);

		return restTemplate
				.exchange("http://localhost:8084/api/makeloan/" + email, HttpMethod.POST, entity, Response.class)
				.getBody();
	}

	@GetMapping("/getRequested/{pageNo}/{itemsPerPage}")
	public Object getAllRequested(@PathVariable int pageNo, @PathVariable int itemsPerPage) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		return restTemplate.exchange("http://localhost:8082/api2/getRequested/" + pageNo + "/" + itemsPerPage,
				HttpMethod.GET, entity, Object.class).getBody();
	}

	@GetMapping("/getSortRequested/{pageNo}/{itemsPerPage}/{fieldName}")
	public Object getAllSortRequested(@PathVariable int pageNo, @PathVariable int itemsPerPage,
			@PathVariable String fieldName) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		return restTemplate.exchange("http://localhost:8082/api2/getSortRequested/" + pageNo + "/" + itemsPerPage +"/"+fieldName,
				HttpMethod.GET, entity, Object.class).getBody();
	}

	@GetMapping("/getCustomer/{pageNo}/{itemsPerPage}")
	public Object getAllCustomer(@PathVariable int pageNo, @PathVariable int itemsPerPage) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		return restTemplate.exchange("http://localhost:8083/api1/getCustomer/" + pageNo + "/" + itemsPerPage,
				HttpMethod.GET, entity, Object.class).getBody();
	}

	@GetMapping("/getSortCustomer/{pageNo}/{itemsPerPage}/{fieldName}")
	public Object getAllSortCustomer(@PathVariable int pageNo, @PathVariable int itemsPerPage,
			@PathVariable String fieldName) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		return restTemplate.exchange("http://localhost:8083/api1/getSortCustomer/" + pageNo + "/" + itemsPerPage + "/" +fieldName,
				HttpMethod.GET, entity, Object.class).getBody();
	}
	
	@SuppressWarnings("unchecked")
	@PutMapping("/forgotpwd/{email}/{password}")
	public Response<User> forgotPassword(@PathVariable String email,@PathVariable String password,@RequestBody User theUser) {
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	      HttpEntity<User> entity = new HttpEntity<User>(theUser,headers);
	      
	      return restTemplate.exchange("http://localhost:8083/api1/forgotpwd/" + email + "/" + password, HttpMethod.PUT, entity, Response.class).getBody();
	   }
	
	@SuppressWarnings("unchecked")
	@PutMapping("/updateUser")
	public Response<User> updateUser(@RequestBody User user) {
		
		HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity<User> entity = new HttpEntity<User>(user,headers);
	      
	      return restTemplate.exchange(
	         "http://localhost:8083/api1/updateUser", HttpMethod.PUT, entity,Response.class).getBody();
	}
	


}
