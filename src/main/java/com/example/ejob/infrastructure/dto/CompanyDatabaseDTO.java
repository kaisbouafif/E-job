package com.example.ejob.infrastructure.dto;

import jakarta.persistence.*;

@Entity(name = "EjobCompany")
@Table(name = "EjobCompany")
public class CompanyDatabaseDTO {

@Column(name = "name")
@Id
private String name;

@Column(name = "industry")
private String industry;

@Column(name = "country")
private String country;

@Column(name = "url")
private String url;

}
