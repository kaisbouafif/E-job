package com.example.ejob.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompanyRestApiDTO {

@JsonProperty(value = "email",required = true)
private String email;

@JsonProperty(value = "name",required = false)
private String name;

@JsonProperty(value = "firstName",required = false)
private String firstName;

@JsonProperty(value = "lastName",required = false)
private String lastName;


@JsonProperty(value = "position",required = false)
private String position;


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPosition() {
        return position;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
