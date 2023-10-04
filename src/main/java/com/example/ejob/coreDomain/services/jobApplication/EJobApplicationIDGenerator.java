package com.example.ejob.coreDomain.services.jobApplication;

import com.example.ejob.coreDomain.entities.EjobApplication;

public interface EJobApplicationIDGenerator {
    String generateEjobApplicationID(EjobApplication ejobApplication);
}
