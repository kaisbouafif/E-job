package com.example.ejob.infrastructure.repositories;

import com.example.ejob.coreDomain.repositoryContracts.CompanyDatabaseRepository;
import com.example.ejob.infrastructure.dao.CompanyJpaDao;
import com.example.ejob.infrastructure.dto.CompanyDatabaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyDatabaseRepositoryImpl  implements CompanyDatabaseRepository  {

    private final CompanyJpaDao companyJpaDao;

    @Autowired
    public CompanyDatabaseRepositoryImpl(CompanyJpaDao companyJpaDao) {
        this.companyJpaDao = companyJpaDao;
    }

    @Override
    public String fetchCompanyUrlByIndustryAndCountry(String industry, String country) {
        return companyJpaDao.findEJobCompanyUrlByIndustryAndCountry(industry,country);
    }

    @Override
    public String fetchCompanyUrlByIndustry(String industry) {
        return companyJpaDao.findEJobCompanyUrlByIndustry(industry);
    }
}
