package com.example.ejob.application.interfaces;

import com.example.ejob.application.RequestModels.EJobApplicationRequestBodyPayload;
import com.example.ejob.application.interceptors.ServicesOutboundExceptionHandler;
import com.example.ejob.coreDomain.entities.EjobApplication;
import com.example.ejob.coreDomain.services.jobApplication.EJobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@RestController
public class EJobApplicationController {


private final ServicesOutboundExceptionHandler  servicesOutboundExceptionHandler;
private final EJobApplicationService eJobApplicationService;





@Autowired
EJobApplicationController(
ServicesOutboundExceptionHandler servicesOutboundExceptionHandler,
 EJobApplicationService EJobApplicationService
){
 this.eJobApplicationService = EJobApplicationService;
 this.servicesOutboundExceptionHandler = servicesOutboundExceptionHandler;
}



@GetMapping("/")
public ResponseEntity<Map<String,Object>> AboutApp(){
    return ResponseEntity
            .ok(Map.of("about","eJob is a platform that helps you find jobs and apply automatically when your job filters match"));
}


@RequestMapping(path = "/apply",method = RequestMethod.POST,consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
public ResponseEntity<Map<String,Object>> postJobApplication(
        @RequestPart EJobApplicationRequestBodyPayload applicationRequestBodyPayload,
        @RequestPart("attachment") MultipartFile attachment
){
    ResponseEntity<Map<String,Object>> response;
    try {
        EjobApplication ejobApplication = new EjobApplication(
                applicationRequestBodyPayload.getEmail(),
                applicationRequestBodyPayload.getSubject(),
                applicationRequestBodyPayload.getMessage(),
                applicationRequestBodyPayload.getCountry(),
                applicationRequestBodyPayload.getIndustry(),
                attachment.getBytes()
        );
        String eJobApplicationID = eJobApplicationService.apply(ejobApplication);
        Map<String,Object> responseData = Map.of("applicationID",eJobApplicationID);
       response = ResponseEntity.ok(responseData);
    } catch (Exception ex){
       response = servicesOutboundExceptionHandler.handle(ex);
    }
return response;
}


}
