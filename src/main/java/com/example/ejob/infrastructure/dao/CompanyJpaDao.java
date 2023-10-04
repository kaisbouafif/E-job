package com.example.ejob.infrastructure.dao;

import com.example.ejob.infrastructure.dto.CompanyDatabaseDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public interface CompanyJpaDao extends CrudRepository<CompanyDatabaseDTO,String> {

    @Query(
            value = "SELECT EjobCompany.url" +
            " FROM EjobCompany" +
            " WHERE EjobCompany.country = :countryParam" +
            " AND EjobCompany .industry = :industryParam",
            nativeQuery = true
    )
    String findEJobCompanyUrlByIndustryAndCountry(@Param("industryParam") String industry,@Param("countryParam") String country);

    @Query(
            value = "SELECT EjobCompany.url" +
                    " FROM EjobCompany" +
                    " WHERE EjobCompany.industry = :industryParam",
            nativeQuery = true
    )
    String findEJobCompanyUrlByIndustry(@Param("industryParam") String industryParam);


}
