package com.example.demo.service;


import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Customer;
import com.example.demo.repository.CustomerRepository;

@Service
public class CustomerService implements UserDetailsService {

	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Customer c = 	customerRepo.findByEmail(email);
		return new User(c.getEmail(), c.getPwd(), Collections.emptyList());
	}

	
	
	public boolean saveCustomer(Customer c) {
		
		 c.setPwd(pwdEncoder.encode(c.getPwd()));
		    return customerRepo.save(c) != null;
				
	}









}
