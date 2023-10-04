package com.example.ejob.application.interfaces;


import com.example.ejob.coreDomain.exceptions.CompaniesNotResolvedException;
import com.example.ejob.coreDomain.exceptions.JobApplicationEmailNotSentException;
import com.example.ejob.coreDomain.services.jobApplication.EJobApplicationService;
import com.example.ejob.infrastructure.di.AppTestConfiguration;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = AppTestConfiguration.class
)
public class EJobApplicationControllerTestCases {

@Autowired
TestRestTemplate testHttpClient;

@Qualifier("ValidJobApplicationRequest")
@Autowired
HttpEntity<MultiValueMap<String,Object>> validJobApplicationPostRequest;
@Qualifier("InvalidJobApplicationRequest")
@Autowired
HttpEntity<MultiValueMap<String,Object>> invalidJobApplicationPostRequest;

@LocalServerPort
int webServerPort;

@MockBean
EJobApplicationService eJobApplicationService;
@Autowired
EJobApplicationController eJobApplicationController;

@BeforeEach
 void init() {
     assumeTrue(validJobApplicationPostRequest != null);
     assumeTrue(invalidJobApplicationPostRequest != null);
     assumeTrue(webServerPort > 0);
     assumeTrue(eJobApplicationController != null);
     assumeTrue(testHttpClient != null);
}

@DisplayName("When making a get request to root endpoint expect a successfull response with data about the app")
@Order(1)
@Test
void When_making_a_get_request_to_root_endpoint_expect_a_successfull_response_with_data_about_the_app(){
    ResponseEntity<Map> response = testHttpClient.getForEntity(
            "http://127.0.0.1:"+webServerPort,
            Map.class
    );

    Assertions.assertAll(
            ()-> assertNotNull(response),
            ()-> {
                assert response != null;
                assertTrue(response.getStatusCode().is2xxSuccessful());
                assertTrue(response.hasBody());
                assertNotNull(response.getBody());
            },
            ()->{
                assert response != null;
               Map<String,Object> responseBody = response.getBody();
               assert responseBody != null;
                assertTrue(responseBody.containsKey("about"));
            }
    );
}


@DisplayName("Given a valid job application body structure then sending a post request to the endpoint should respond successfully")
@Order(2)
@Test
void Given_a_valid_job_application_body_structure_then_sending_a_post_request_to_the_endpoint_should_respond_successfully() throws IOException, CompaniesNotResolvedException, JobApplicationEmailNotSentException {

    when(eJobApplicationService.apply(any()))
   .thenReturn("MockID");

    ResponseEntity<Map> response = testHttpClient.postForEntity(
                "http://127.0.0.1:"+webServerPort+"/apply",
            validJobApplicationPostRequest,
           Map.class
        );

        Assertions.assertAll(
                ()-> assertNotNull(response),
                ()-> {
                    System.out.println(response);
                    assertTrue(response.getStatusCode().is2xxSuccessful());
                    assertTrue(response.hasBody());
                    Assertions.assertTrue(response.getBody().containsKey("applicationID"));
                }
        );
}

@DisplayName("Given an invalid job application body structure then sending a post request to the endpoint should respond successfully")
@Order(3)
 @Test
void Given_an_invalid_job_application_body_structure_then_sending_a_post_request_to_the_endpoint_should_respond_successfully() throws IOException, CompaniesNotResolvedException, JobApplicationEmailNotSentException {
        when(eJobApplicationService.apply(any()))
                .thenReturn("MockID");

        ResponseEntity<Map> response = testHttpClient.postForEntity(
                "http://127.0.0.1:"+webServerPort+"/apply",
                invalidJobApplicationPostRequest,
                Map.class
        );

        Assertions.assertAll(
                ()-> assertNotNull(response),
                ()-> {
                    assert response != null;
                    System.out.println(response);
                    assertTrue(response.getStatusCode().isError());
                }
        );
 }





}
