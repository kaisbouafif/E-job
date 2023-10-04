package com.example.ejob.application.RequestModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EJobApplicationRequestBodyPayload  {

    @JsonProperty("email")
    private String email;

    @JsonProperty("subject")
    private String subject;


    @JsonProperty("message")
    private String message;


    @JsonProperty("country")
    private String country;


    @JsonProperty("industry")
    private String industry;

    @Override
    public String toString() {
        return "EJobApplicationRequestBodyPayload{" +
                "subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", country='" + country + '\'' +
                ", industry='" + industry + '\'' +
                '}';
    }

    public EJobApplicationRequestBodyPayload(
            String email,
            String subject,
            String message,
            String country,
            String industry
    ) {
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.country = country;
        this.industry = industry;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public String getCountry() {
        return country;
    }

    public String getIndustry() {
        return industry;
    }

    public String getEmail() {
        return email;
    }
}
