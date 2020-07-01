package com.redditClone.dto;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ComponentScan;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@ComponentScan(basePackages = {"com.redditClone.dto.RegisterRequest"})

public class RegisterRequest {
	private String email;
	private String username;
	private String password;


}
