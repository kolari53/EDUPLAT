package com.CodeShake.Edushake.Controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.CodeShake.Edushake.Properties;


import java.util.*;

@RestController
public class TeachersController {

    @GetMapping("/teachers")
    public JsonNode getTeachers(){
        String url = Properties.HOSTADDRESS.getProperties() + "/source/teacher";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",Properties.TOKEN.getProperties());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String students = restTemplate.getForObject(url, String.class, headers);

        HttpEntity <String> entity = new HttpEntity <> (students, headers);
        ResponseEntity <JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class); 

        return Arrays.asList(response.getBody()).get(0);
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<JsonNode> getTeacherById(@PathVariable(name = "id") String id) {
        String url = Properties.HOSTADDRESS.getProperties() + "/source/teacher/" + id;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",Properties.TOKEN.getProperties());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String teacher = restTemplate.getForObject(url, String.class, headers);

        HttpEntity <String> entity = new HttpEntity <> (teacher, headers);
        ResponseEntity <JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class); 

        return response;
    }
    @PostMapping("/createTeachersUser")
    public String createNewTeacherUser(List<JsonNode> teachersList){
       
        String url = Properties.HOSTADDRESS.getProperties() + "/target/user";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",Properties.TOKEN.getProperties());
        headers.set("Body", teachersList.toString());
        headers.setContentType(MediaType.APPLICATION_JSON);


        // send POST request
        HttpEntity<String> entity = new HttpEntity<>(teachersList.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        return response.getStatusCode().toString();
        // if (response.getStatusCode() == HttpStatus.CREATED) {
        //     System.out.println(response.getBody());
        //     return "Request Successful";
        // } else {
        //     System.out.println("Request Failed");
        //     System.out.println(response.getStatusCode());
        //     return "Request Failed";
        // }
    }
}
