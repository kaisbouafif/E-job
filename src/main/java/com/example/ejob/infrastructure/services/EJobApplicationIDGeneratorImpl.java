package com.example.ejob.infrastructure.services;

import com.example.ejob.coreDomain.entities.EjobApplication;
import com.example.ejob.coreDomain.services.jobApplication.EJobApplicationIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
public class EJobApplicationIDGeneratorImpl implements EJobApplicationIDGenerator {


     private final PasswordEncoder passwordEncoder;

    @Autowired
    public EJobApplicationIDGeneratorImpl(
            PasswordEncoder passwordEncoder
    ){
     this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String generateEjobApplicationID(EjobApplication ejobApplication) {
        return passwordEncoder.encode(
                ejobApplication.getSubject()+Instant.now().toString()
        );
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .build();
    }
}
