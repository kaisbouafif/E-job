package com.example.ejob.coreDomain.services.companies;

import com.example.ejob.coreDomain.exceptions.CompaniesNotResolvedException;

import java.util.List;

public interface CompaniesService {
    String getCompanyEmailByIndustryAndCountry(String industry, String country) throws CompaniesNotResolvedException;

}
