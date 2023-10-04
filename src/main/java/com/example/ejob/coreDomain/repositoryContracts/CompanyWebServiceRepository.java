package com.example.ejob.coreDomain.repositoryContracts;

import com.example.ejob.coreDomain.exceptions.CompaniesNotResolvedException;

public interface CompanyWebServiceRepository {

    String fetchCompanyEmailByUrl(String url) throws CompaniesNotResolvedException;

}
