package com.example.ejob.coreDomain.entities;

import com.example.ejob.coreDomain.exceptions.InvalidJobApplicationRulesException;

import java.util.regex.Pattern;

public class EjobApplication {

private final Pattern emailPattern = Pattern.compile("[a-zA-Z1-9_.-]+@[a-zA-Z]+\\.[a-zA-Z]+");

private final byte[] attachment;

private final String email;

private final String subject;
private final String message;
private final String country;
private final String industry;
private final long attachmentSize;

    public EjobApplication(
            String email,
            String subject,
            String message,
            String country,
            String industry,
            byte[] attachment
    ) throws InvalidJobApplicationRulesException {
        this.email = email;
        this.attachment = attachment;
        this.subject = subject;
        this.message = message;
        this.country = country;
        this.industry = industry;
        this.attachmentSize = attachment.length;
        checkJobApplicationConstraints();
    }

    public String getEmail() {
        return email;
    }

    private boolean isAttachmentSizeValid() {
       return attachmentSize > 200*1000 && attachmentSize < 1000*1000;
   }

    private void checkJobApplicationConstraints() throws InvalidJobApplicationRulesException {
        if (this.subject.isBlank())  throw new InvalidJobApplicationRulesException("Subject is required");
        if (this.email.isBlank()) throw new InvalidJobApplicationRulesException("application email is required");
        if (!isEmailFormatValid()) throw new InvalidJobApplicationRulesException("application email format should be respected");
        if ( this.industry.isBlank()) throw new InvalidJobApplicationRulesException("Company industry is required");
        if (this.message.isBlank() ) throw new InvalidJobApplicationRulesException("Empty content !");
        if (!isAttachmentSizeValid()) throw new InvalidJobApplicationRulesException("Attachement file size isn't supported");
    }


    private boolean isEmailFormatValid() {
       return emailPattern
                .matcher(this.email)
                .matches();
    }

    public byte[] getAttachment() {
        return attachment;
    }
    public String getSubject() {
        return subject;
    }
    public String getMessage() {
        return message;
    }

    public String getCountry() {
        return country;
    }

    public String getIndustry() {
        return industry;
    }

    @Override
    public String toString() {
        return "EjobApplication{" +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", country='" + country + '\'' +
                ", industry='" + industry + '\'' +
                '}';
    }


}
