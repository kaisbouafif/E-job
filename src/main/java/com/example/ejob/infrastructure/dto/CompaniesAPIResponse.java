package com.example.ejob.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CompaniesAPIResponse {

@JsonProperty("success")
private boolean isSuccessfull = false;

@JsonProperty("remaining_requests")
private int remaining_requests;

@JsonProperty("domain")
private String domain;

@JsonProperty("leads")
private List<CompanyRestApiDTO> companies;

    public List<CompanyRestApiDTO> getCompanies() {
        return companies;
    }

    public boolean isSuccessfull() {
        return isSuccessfull;
    }

    public int getRemaining_requests() {
        return remaining_requests;
    }

    public String getDomain() {
        return domain;
    }
}
