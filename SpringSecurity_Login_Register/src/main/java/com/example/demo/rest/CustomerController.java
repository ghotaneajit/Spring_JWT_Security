package com.example.demo.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.AuthRequest;
import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;
import com.example.demo.service.JwtService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	private CustomerService custService;
	
	@Autowired
    private JwtService jwtService;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/Register")
	public ResponseEntity<String> registerCustomer(@RequestBody Customer c){
		boolean status= custService.saveCustomer(c);
		if(status) {
			return new ResponseEntity<>("success", HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>("failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPwd())
        );
        if (authentication.isAuthenticated()) {
        	return  jwtService.generateToken(authRequest.getEmail());
         
        
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
	
	 @GetMapping("/welcome")
	    public String welcome() {
	        return "Welcome this endpoint is not secure";
	    }
	
	
	
//	@PostMapping("/Login")
//	public ResponseEntity<String> loginCustomer(@RequestBody Customer c){
//		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(c.getEmail(), c.getPwd()); 
//		 Authentication authenticate = authenticationManager.authenticate(token);
//		 boolean status = authenticate.isAuthenticated();
//		 if(status) {
//			 return new ResponseEntity<>("success", HttpStatus.OK);
//		 }
//		 else {
//			 return new ResponseEntity<>("failed", HttpStatus.BAD_REQUEST);
//		 }
//	}
//	
}
