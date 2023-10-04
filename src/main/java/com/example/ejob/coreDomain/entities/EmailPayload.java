package com.example.ejob.coreDomain.entities;


public class EmailPayload {

private String senderEmail;

private String[] recieversEmail;

private String subject;

private String content;

private byte[] attachement;


    private EmailPayload(){

    }

    public EmailPayload(String senderEmail, String[] recieversEmail, String subject, String content, byte[] attachement) {
        this.senderEmail = senderEmail;
        this.recieversEmail = recieversEmail;
        this.subject = subject;
        this.content = content;
        this.attachement = attachement;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String[] getRecieversEmail() {
        return recieversEmail;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public byte[] getAttachement() {
        return attachement;
    }
}
