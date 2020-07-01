package com.redditClone.security;

import com.redditClone.exception.SpringRedditException;
import com.redditClone.model.User;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import sun.security.tools.KeyStoreUtil;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {
    private KeyStore keyStore;
    public String generateToken(Authentication authentication){
     User principal = (User)authentication.getPrincipal();
     return Jwts.builder()
             .setSubject(principal.getUsername())
             .signWith(getPrivateKey())
             .compact();
    }
    @PostConstruct
    public void init(){
        try{
            keyStore = KeyStore.getInstance("JKS");
          InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());

        }
        catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e){
            throw new SpringRedditException("Exception occurred while loading keystore", e);

        }
    }

    private PrivateKey getPrivateKey(){
        try{
            return (PrivateKey) keyStore.getKey("/springblog.jks","secret".toCharArray());

        }
        catch (KeyStoreException |NoSuchAlgorithmException | UnrecoverableKeyException e){
            throw new SpringRedditException("Exception occured while retrieving public key from keystore", e);

        }

    }
}
