package com.example.ejob.coreDomain.services.jobApplication;

import com.example.ejob.coreDomain.entities.EjobApplication;
import com.example.ejob.coreDomain.exceptions.CompaniesNotResolvedException;
import com.example.ejob.coreDomain.exceptions.InvalidJobApplicationRulesException;
import com.example.ejob.coreDomain.exceptions.JobApplicationEmailNotSentException;
import com.example.ejob.coreDomain.services.companies.CompaniesService;
import com.example.ejob.coreDomain.services.email.EmailService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EJobApplicationServiceTestCases{


  @Mock  EmailService emailService;
  @Mock  CompaniesService companiesService;
  @Mock  EJobApplicationIDGenerator eJobApplicationIDGenerator;


 private EjobApplication eJobApplication1;
 private EjobApplication eJobApplication2;

 private EjobApplication eJobApplication3;
 @InjectMocks
 EJobApplicationService eJobApplicationService = new EJobApplicationServiceImpl(
      emailService,
      companiesService,
      eJobApplicationIDGenerator
 );

    public EJobApplicationServiceTestCases() {

        try {
            eJobApplication1 = new EjobApplication(
                    "test@gmail.com",
                    "testMessage",
                    "testCountry",
                    "Tunisia",
                    "greenTech",
                    new byte[400000]
            );
            eJobApplication2 = new EjobApplication(
                    "test@gmail.com",
                    "testsubject",
                    "testMessage",
                    "USA",
                    "health",
                    new byte[400000]
            );
            eJobApplication3 = new EjobApplication(
                    "test@gmail.com",
                    "testMessage",
                    "testCountry",
                    "",
                    "finTech",
                    new byte[400000]
            );
        } catch (InvalidJobApplicationRulesException e) {
           e.printStackTrace();
        }
    }


@BeforeEach
void initOnEach() {
    // we're assuming this object does not throw any exception since it is tested in a separate unit test.
    assumeTrue(eJobApplication1 != null, () ->
            "eJobApplication1 has not been initialized due to broken constraints ! check its related unit tests !"
    );
    assumeTrue(eJobApplication2 != null, () ->
            "eJobApplication2 has not been initialized due to broken constraints ! check its related unit tests !"
    );
    assumeTrue(eJobApplication3 != null, () ->
            "eJobApplication3 has not been initialized due to broken constraints ! check its related unit tests !"
    );

}

    @DisplayName("Given application filters that don't match any company then job application should fail")
    @Order(1)
    @Test
    void Given_application_filters_not_matching_any_company_then_exception_should_be_thrown() throws CompaniesNotResolvedException {

            when(companiesService.getCompanyEmailByIndustryAndCountry(
                    eJobApplication1.getIndustry(),
                    eJobApplication1.getCountry()
               ))
            .thenThrow(CompaniesNotResolvedException.class);
        Assertions.assertThrows(CompaniesNotResolvedException.class,()->eJobApplicationService.apply(eJobApplication1));
    }

@DisplayName("Given an empty country search filter then the job application should succeed")
@Order(2)
@Test
void Given_company_search_filter_then_application_should_succeed() throws CompaniesNotResolvedException, JobApplicationEmailNotSentException {

        try {
            when(companiesService.getCompanyEmailByIndustryAndCountry(
                    eJobApplication2.getIndustry(),
                    eJobApplication2.getCountry()
            ))
           .thenReturn("recruitementService@square.com");
        } catch (com.example.ejob.coreDomain.exceptions.CompaniesNotResolvedException e) {
            throw new RuntimeException(e);
        }
        when(eJobApplicationIDGenerator.generateEjobApplicationID(eJobApplication2))
            .thenReturn("ID:A6BN7");

    Assertions.assertEquals(
            "ID:A6BN7",
            eJobApplicationService.apply(eJobApplication2)
    );
}

@DisplayName("Given an empty country search filter then the job application should succeed")
@Order(3)
@Test
void Given_empty_country_search_filter_then_all_companies_are_returned_by_industry_only() throws CompaniesNotResolvedException, JobApplicationEmailNotSentException {

            when(companiesService.getCompanyEmailByIndustryAndCountry(
                    eJobApplication3.getIndustry(),
                    eJobApplication3.getCountry()
            ))
           .thenReturn("recruitementService@any.com");

        when(eJobApplicationIDGenerator.generateEjobApplicationID(eJobApplication3))
            .thenReturn("ID:A6BN7");

    Assertions.assertEquals(
            "ID:A6BN7",
            eJobApplicationService.apply(eJobApplication3)
    );
}


}
