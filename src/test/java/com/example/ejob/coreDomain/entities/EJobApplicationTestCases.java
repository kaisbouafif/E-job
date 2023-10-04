package com.example.ejob.coreDomain.entities;


import com.example.ejob.coreDomain.exceptions.InvalidJobApplicationRulesException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;



public class EJobApplicationTestCases {

    @Order(1)
    @DisplayName("When job application email is empty then test should fail")
    @Test
    void When_job_application_email_isEmpty_then_test_should_fail() {
        Assertions.assertThrows(InvalidJobApplicationRulesException.class, () -> new EjobApplication(
                "  ",
                "subject",
                "no message",
                "tunisia",
                "IT",
                new byte[500000]
        ));
    }

    @Order(2)
    @DisplayName("When job application email is invalid then test should fail")
    @Test
    void When_job_application_email_invalid_then_test_should_fail() {
        Assertions.assertThrows(InvalidJobApplicationRulesException.class, () -> new EjobApplication(
                "emailemail@gmail.com",
                "IT",
                "no message",
                "tunisia",
                "IT",
                new byte[500000]
        ));
    }


    @Order(3)
    @DisplayName("When job application subject is empty then test should fail")
    @Test
    void When_job_application_subject_isEmpty_then_test_should_fail() {
        Assertions.assertThrows(InvalidJobApplicationRulesException.class, () -> new EjobApplication(
                "emailemail@gmail.com",
                " ",
                "no message",
                "tunisia",
                "IT",
                new byte[500000]
        ));
    }

    @Order(4)
    @DisplayName("When job application message is empty then test should fail")
    @Test
    void When_job_application_message_isEmpty_then_test_should_fail() {
        Assertions.assertThrows(InvalidJobApplicationRulesException.class, () -> new EjobApplication(
                "email@gmail.com",
                "no subject",
                " ",
                "tunisia",
                "IT",
                new byte[500000]
        ));
    }

    @Order(5)
    @DisplayName("When job application does not specify the company's industry then test should fail")
    @Test
    void When_job_application_does_not_specify_company_industry_then_test_should_fail() {
        Assertions.assertThrows(InvalidJobApplicationRulesException.class, () -> new EjobApplication(
                "email@gmail.com",
                "no subject",
                "no message",
                "tunisia",
                "  ",
                new byte[500000]
        ));
    }

    @Order(6)
    @DisplayName("When job application attachment's size is too big then test should fail")
    @Test
    void When_job_application_attachement_size_is_too_big_then_test_should_fail() {
        Assertions.assertThrows(InvalidJobApplicationRulesException.class, () -> new EjobApplication(
                "email@gmail.com",
                "no subject",
                "no message",
                "tunisia",
                "IT",
                new byte[3000000]
        ));
    }

    @Order(7)
    @DisplayName("When job application attachment's size is too small then test should fail")
    @Test
    void When_job_application_attachement_size_is_too_small_then_test_should_fail() {
        Assertions.assertThrows(InvalidJobApplicationRulesException.class, () -> new EjobApplication(
                "email@gmail.com",
                "no subject",
                "no message",
                "tunisia",
                "IT",
                new byte[10000]
        ));
    }

    @Order(8)
    @DisplayName("When job application constraints are respected then test should succeed")
    @Test
    void When_job_application_constraints_are_respected_then_test_should_succeed() {
        Assertions.assertDoesNotThrow( () -> new EjobApplication(
                "email@gmail.com",
                "no subject",
                "no message",
                "tunisia",
                "IT",
                new byte[250000]
        ));
    }



}
