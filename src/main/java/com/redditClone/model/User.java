package com.redditClone.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ComponentScan;

import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.GenerationType.IDENTITY;

//@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
//@ComponentScan
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long userId;
	@NotBlank(message = "Username is required")
	private String username;
	@NotBlank(message = "Password is required")
	private String password;
	@Email
	@NotEmpty(message = "Email is required")
	private String email;
	private Instant created;
	private boolean enabled;


}
