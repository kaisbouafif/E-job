package com.example.ejob.coreDomain.services.email;

import com.example.ejob.coreDomain.entities.EmailPayload;

public interface EmailService {
    boolean sendEmail(EmailPayload emailPayload);
}
