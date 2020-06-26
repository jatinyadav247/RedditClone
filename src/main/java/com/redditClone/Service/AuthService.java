package com.redditClone.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.redditClone.dto.AuthenticationResponse;
import com.redditClone.dto.LoginRequest;
import com.redditClone.exception.SpringRedditException;
import com.redditClone.model.NotificationEmail;
import com.redditClone.security.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.redditClone.dto.RegisterRequest;
import com.redditClone.model.User;
import com.redditClone.repository.UserRepository;
import com.redditClone.repository.VerificationTokenRepository;
import com.redditClone.model.VerificationToken;


import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;

//@EnableJpaRepositories
@AllArgsConstructor
@Slf4j
@Service
public class AuthService {
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final VerificationTokenRepository verificationTokenRepository;
	private final MailService mailService;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	//private final RefreshTokenService refreshTokenService;

	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);

		userRepository.save(user);

		String token = generateVerificationToken(user);
		mailService.sendMail(new NotificationEmail("Please Activate your Account",
				user.getEmail(), "Thank you for signing up to Spring Reddit, " +
				"please click on the below url to activate your account : " +
				"http://localhost:8082/api/auth/accountVerification/" + token));
	}



	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);

		verificationTokenRepository.save(verificationToken);
		return token;
	}

	public void verifyAccount(String token){
		//so here we have to query verification token repository by token which we recieve as an input to the method
      Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
      // In Case if Entity Does not exist we can call the or else Throw method and throw the custom exception with message as Invalid Token
		//verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
// now we have to query the corresponding user who is associated with this token and enable that user
		//fetchUserAndEnable(verificationToken.get());
		fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token")));

	}
	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken){
		String username = verificationToken.getUser().getUsername();
		User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not found with name - " + username));
		user.setEnabled(true);
		userRepository.save(user);
	}
	public AuthenticationResponse login(LoginRequest loginRequest){
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String token = jwtProvider.generateToken(authenticate);
		return new AuthenticationResponse(token,loginRequest.getUsername());

	}




}
