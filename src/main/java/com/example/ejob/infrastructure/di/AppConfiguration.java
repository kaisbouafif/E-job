package com.example.ejob.infrastructure.di;

import com.example.ejob.application.interceptors.ServicesOutboundExceptionHandler;
import com.example.ejob.coreDomain.exceptions.CompaniesNotResolvedException;
import com.example.ejob.coreDomain.exceptions.InvalidJobApplicationRulesException;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

@EnableJpaRepositories(basePackages = "com.example.ejob.infrastructure.dao")
@Configuration
public class AppConfiguration {


@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Bean
MailjetClient provideMailClient(
        @Value("${mailjet.api_key}") String apiKey,
        @Value("${mailjet.api_secret_key}")  String apiSecretKey
) {
    ClientOptions options = ClientOptions.builder()
            .apiKey(apiKey)
            .apiSecretKey(apiSecretKey)
            .build();

   return new MailjetClient(options);
}

@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Bean
PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }

@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Bean
ServicesOutboundExceptionHandler servicesOutboundExceptionHandler() {
        return exception -> {
            exception.printStackTrace();
            if (exception instanceof InvalidJobApplicationRulesException || exception instanceof CompaniesNotResolvedException){
                return ResponseEntity
                        .unprocessableEntity()
                        .body(Map.of("Error",exception.getMessage()));
            }  else {
                return ResponseEntity
                        .internalServerError()
                        .build();
            }
        };
    }



}
