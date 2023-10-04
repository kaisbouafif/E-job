package com.example.ejob.coreDomain.services.jobApplication;

import com.example.ejob.coreDomain.entities.EjobApplication;
import com.example.ejob.coreDomain.exceptions.CompaniesNotResolvedException;
import com.example.ejob.coreDomain.exceptions.JobApplicationEmailNotSentException;

public interface EJobApplicationService {
    String apply(EjobApplication application) throws CompaniesNotResolvedException, JobApplicationEmailNotSentException;
}
