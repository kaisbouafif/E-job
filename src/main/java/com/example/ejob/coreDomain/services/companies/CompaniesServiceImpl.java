package com.example.ejob.coreDomain.services.companies;

import com.example.ejob.coreDomain.exceptions.CompaniesNotResolvedException;
import com.example.ejob.coreDomain.repositoryContracts.CompanyDatabaseRepository;
import com.example.ejob.coreDomain.repositoryContracts.CompanyWebServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompaniesServiceImpl implements CompaniesService{

    private final CompanyDatabaseRepository companyDatabaseRepository;

    private final CompanyWebServiceRepository companyWebServiceRepository;


    @Autowired
    public CompaniesServiceImpl(CompanyDatabaseRepository companyDatabaseRepository, CompanyWebServiceRepository companyWebServiceRepository) {
        this.companyDatabaseRepository = companyDatabaseRepository;
        this.companyWebServiceRepository = companyWebServiceRepository;
    }

    @Override
    public String getCompanyEmailByIndustryAndCountry(String industry, String country) throws CompaniesNotResolvedException {
      String companyUrl;
        if (country.isBlank()){
            companyUrl = companyDatabaseRepository.fetchCompanyUrlByIndustry(
                    industry
            );
        } else {
            companyUrl = companyDatabaseRepository.fetchCompanyUrlByIndustryAndCountry(
                    industry,country
            );
        }
        return companyWebServiceRepository.fetchCompanyEmailByUrl(companyUrl);
    }
}
