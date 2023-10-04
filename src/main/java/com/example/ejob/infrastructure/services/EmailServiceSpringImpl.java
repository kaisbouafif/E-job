package com.example.ejob.infrastructure.services;

import com.example.ejob.coreDomain.services.email.EmailService;
import com.example.ejob.coreDomain.entities.EmailPayload;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.transactional.*;
import com.mailjet.client.transactional.response.SendEmailsResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;


@Service("SpringImpl")
public class EmailServiceSpringImpl implements EmailService {

    private final MailjetClient mailClient;

    @Autowired
    EmailServiceSpringImpl(
            MailjetClient mailClient
    ){
        this.mailClient = mailClient;
    }

    @Override
    public boolean sendEmail(EmailPayload emailPayload)  {
        try {
            Collection<SendContact> recieversContacts = Arrays.stream(emailPayload.getRecieversEmail())
                    .map((email) -> new SendContact(email, email))
                    .toList();

            SendContact sendContact = new SendContact(emailPayload.getSenderEmail(),emailPayload.getSenderEmail());

            Attachment attachment = Attachment.fromInputStream(
                    new ByteArrayInputStream(emailPayload.getAttachement()),
                    "candidate_cv",
                    "application/pdf"
            );

            TransactionalEmail message = TransactionalEmail
                    .builder()
                    .to(recieversContacts)
                    .from(sendContact)
                    .priority(1)
                    .textPart(emailPayload.getContent())
                    .attachment(attachment)
                    .subject(emailPayload.getSubject())
                    .trackOpens(TrackOpens.ENABLED)
                    .header("test-header-key", "test-value")
                    .customID("custom-id-value")
                    .build();

            SendEmailsRequest request = SendEmailsRequest
                    .builder()
                    .message(message) // you can add up to 50 messages per request
                    .build();
            SendEmailsResponse response = request.sendWith(mailClient);
            return true;
        } catch (MailjetException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
