package com.example.ejob.infrastructure.di;


import com.example.ejob.application.RequestModels.EJobApplicationRequestBodyPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


@TestConfiguration
public class AppTestConfiguration {

@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Bean
FileSystem provideVirtualFileSystem(){
    return Jimfs.newFileSystem(Configuration.forCurrentPlatform());
}

@Bean("300kbFile")
FileSystemResource provide300KbMockFileResource(
        FileSystem virtualFileSystem
){
    try {
        String fileName = "mock_file.pdf";
        Path pathToStore = virtualFileSystem.getPath("");
        Path path;
        path = Files.createFile(pathToStore.resolve(fileName));
        Files.write(path,"a".repeat(300000).getBytes());
        return new FileSystemResource(path);
    } catch (Exception ex){
        ex.printStackTrace();
        return null;
    }
}

@Bean("ValidJobApplicationRequest")
HttpEntity<MultiValueMap<String,Object>> provideValidRequestEntityForJobApplicationPostMapping(
     @Qualifier("300kbFile") FileSystemResource mockFile
){
    try {
     HttpHeaders headers = new HttpHeaders();
    headers.setAccept(List.of(MediaType.APPLICATION_JSON,MediaType.MULTIPART_FORM_DATA));
    EJobApplicationRequestBodyPayload eJobApplicationRequestBodyPayload = new EJobApplicationRequestBodyPayload(
            "email@gmail.com",
            "test subject",
            "test message",
            "test country",
            "Fintech"
    );
    ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(eJobApplicationRequestBodyPayload);
        MultiValueMap<String,Object> eJobApplicationRequestBody = new LinkedMultiValueMap<>();
        eJobApplicationRequestBody.set("attachment",mockFile);
        eJobApplicationRequestBody.set("applicationRequestBodyPayload",eJobApplicationRequestBodyPayload);

        HttpEntity<MultiValueMap<String,Object>> httpRequest = new HttpEntity<>(eJobApplicationRequestBody, headers);
    return  httpRequest;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}


    @Bean("InvalidJobApplicationRequest")
    HttpEntity<MultiValueMap<String,Object>> provideInvalidRequestEntityForJobApplicationPostMapping(
            @Qualifier("300kbFile") FileSystemResource mockFile
    ){
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_JSON,MediaType.MULTIPART_FORM_DATA));
            EJobApplicationRequestBodyPayload eJobApplicationRequestBodyPayload = new EJobApplicationRequestBodyPayload(
                    "email@gmail.com",
                    "test subject",
                    "test message",
                    "test country",
                    " "
            );
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(eJobApplicationRequestBodyPayload);
            MultiValueMap<String,Object> eJobApplicationRequestBody = new LinkedMultiValueMap<>();
            eJobApplicationRequestBody.set("attachment",mockFile);
            eJobApplicationRequestBody.set("applicationRequestBodyPayload",eJobApplicationRequestBodyPayload);

            HttpEntity<MultiValueMap<String,Object>> httpRequest = new HttpEntity<>(eJobApplicationRequestBody, headers);
            return  httpRequest;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
