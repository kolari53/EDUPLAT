package com.CodeShake.Edushake.Controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.CodeShake.Edushake.Properties;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.*;

/*
 *  This class is where all the operations related to "Student" are done.
 * @author:Kolari53
 */
@RestController
public class StudentsController {


    //This method returns  all students with the specified endpoint.
    @GetMapping("/students")
    public JsonNode getStudents() {
        String url = Properties.HOSTADDRESS.getProperties()+ "/source/student";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",Properties.TOKEN.getProperties());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String students = restTemplate.getForObject(url, String.class, headers);

        HttpEntity<String> entity = new HttpEntity<>(students, headers);
        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);

        return Arrays.asList(response.getBody()).get(0);
    }

    /*
     * @Param : Student_id
     * @Description : This method finds the desired student using the input parameter and the endpoint.
     */
    @GetMapping("/students/{id}")
    public ResponseEntity<JsonNode> getStudentById(@PathVariable(name = "id") String id) {
        String url = Properties.HOSTADDRESS.getProperties()+"/sync/source/student/" + id;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",Properties.TOKEN.getProperties());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String student = restTemplate.getForObject(url, String.class, headers);

        HttpEntity<String> entity = new HttpEntity<>(student, headers);
        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);

        // System.out.println(response.getBody().toString());
        return response;
    }

    @PostMapping("/createStudentsUser")
    public String createNewStudentUser(List<JsonNode> studentsList){
       
        String url = Properties.HOSTADDRESS.getProperties() + "/target/user";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",Properties.TOKEN.getProperties());
        headers.set("Body", studentsList.toString());
        headers.setContentType(MediaType.APPLICATION_JSON);


        // send POST request
        HttpEntity<String> entity = new HttpEntity<>(studentsList.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            //  System.out.println(response.getBody());
             return response.getStatusCode().toString();
        } else {
            //  System.out.println(response.getStatusCode());
             return "Request Failed";
         }
    }
}
