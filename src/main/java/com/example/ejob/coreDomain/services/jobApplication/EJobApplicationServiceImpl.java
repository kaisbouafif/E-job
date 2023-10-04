package com.example.ejob.coreDomain.services.jobApplication;

import com.example.ejob.coreDomain.entities.EjobApplication;
import com.example.ejob.coreDomain.exceptions.CompaniesNotResolvedException;
import com.example.ejob.coreDomain.exceptions.JobApplicationEmailNotSentException;
import com.example.ejob.coreDomain.services.companies.CompaniesService;
import com.example.ejob.coreDomain.services.email.EmailService;
import com.example.ejob.coreDomain.entities.EmailPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EJobApplicationServiceImpl implements EJobApplicationService {

    EmailService emailService;
    CompaniesService companiesService;
    EJobApplicationIDGenerator eJobApplicationIDGenerator;

    @Autowired
    EJobApplicationServiceImpl(
            @Qualifier("SpringImpl") EmailService  emailService,
            CompaniesService companiesService,
            EJobApplicationIDGenerator eJobApplicationIDGenerator
    ){
      this.emailService = emailService;
      this.companiesService = companiesService;
      this.eJobApplicationIDGenerator = eJobApplicationIDGenerator;
    }

    @Override
    public String  apply(EjobApplication application) throws CompaniesNotResolvedException, JobApplicationEmailNotSentException {
        String companyEmail  = companiesService.getCompanyEmailByIndustryAndCountry(
                application.getIndustry(),
                application.getCountry()
        );
        if (companyEmail == null || companyEmail.isBlank()) throw  new CompaniesNotResolvedException("No company found !");
        EmailPayload email = new EmailPayload(
                "#your email",
                new String[]{companyEmail},
                application.getSubject(),
                application.getMessage(),
                application.getAttachment()
        );
       boolean isEmailSent = emailService.sendEmail(email);
       if (!isEmailSent) throw new JobApplicationEmailNotSentException("Sending email failed !");*/
        return eJobApplicationIDGenerator.generateEjobApplicationID(application);
    }
}
