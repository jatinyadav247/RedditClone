package com.redditClone.Service;

import com.redditClone.exception.SpringRedditException;
import com.redditClone.model.User;
import com.redditClone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserDetailsServiceImp1 implements UserDetailsService {
    private  final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username)  {

            Optional<User> userOptional =  userRepository.findByUsername(username);
            User user = userOptional.orElseThrow(()-> new SpringRedditException("No user " +
                    "Found with username : " + username));

            return new org.springframework.security
                    .core.userdetails.User(user.getUsername(), user.getPassword(),
                    user.isEnabled(), true, true,
                    true, getAuthorities("USER"));


        }






    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }
}