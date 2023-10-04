package com.example.ejob.infrastructure.repositories;

import com.example.ejob.coreDomain.exceptions.CompaniesNotResolvedException;
import com.example.ejob.coreDomain.repositoryContracts.CompanyWebServiceRepository;
import com.example.ejob.infrastructure.dto.CompaniesAPIResponse;
import com.example.ejob.infrastructure.dto.CompanyRestApiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;


@Repository
public class CompanyWebServiceRepositoryImpl  implements CompanyWebServiceRepository {


    @Value("${companies_api.server.url}")
    private String companiesAPIbaseUrl;

    @Value("${companies_api.token}")
    private String companiesAPIToken;

    private final RestTemplate restClient;


    @Autowired
    public CompanyWebServiceRepositoryImpl(RestTemplate restClient) {
        this.restClient = restClient;
    }

    @Override
    public String fetchCompanyEmailByUrl(String url) throws CompaniesNotResolvedException {
        ResponseEntity<CompaniesAPIResponse> response = restClient.getForEntity(
                companiesAPIbaseUrl+"?token="+companiesAPIToken+"&domain="+url,
                CompaniesAPIResponse.class
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody().isSuccessfull()){
            Optional<String> result = response.getBody().getCompanies()
                    .stream()
                    .filter(companyRestApiDTO -> companyRestApiDTO.getPosition()
                            .toLowerCase(Locale.getDefault())
                            .contains("recruitement") ||
                            companyRestApiDTO.getPosition().contains("hiring manager")||
                            companyRestApiDTO.getPosition().contains("human resources")||
                            companyRestApiDTO.getPosition().contains("recruiting")
                    )
                    .map(CompanyRestApiDTO::getEmail)
                    .findFirst();
            if (result.isPresent()) return result.get(); else throw new CompaniesNotResolvedException("No recruiting staff found for this company!");
        } else throw new CompaniesNotResolvedException("Error fetching companies");
    }
}
