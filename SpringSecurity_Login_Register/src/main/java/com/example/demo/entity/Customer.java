package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Customer {
			
	
			@Id
			@GeneratedValue(strategy = GenerationType.AUTO)
			private Integer cid;
			private String name;
			private String email;
			private String pwd;
			private Long phno;
	
}
