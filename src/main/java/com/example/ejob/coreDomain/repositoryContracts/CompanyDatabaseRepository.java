package com.example.ejob.coreDomain.repositoryContracts;

public interface CompanyDatabaseRepository {

    String fetchCompanyUrlByIndustryAndCountry(String industry,String country);
    String fetchCompanyUrlByIndustry(String industry);

}
