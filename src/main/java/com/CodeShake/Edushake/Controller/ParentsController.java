package com.CodeShake.Edushake.Controller;

import java.util.List;

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

import com.CodeShake.Edushake.Properties;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class ParentsController {
    @GetMapping("/parents/{id}")
    public JsonNode getParentById(@PathVariable(name = "id") String id) {
        String url = Properties.HOSTADDRESS.getProperties() + "/government/person/" + id;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",Properties.TOKEN.getProperties());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String parent = restTemplate.getForObject(url, String.class, headers);

        HttpEntity <String> entity = new HttpEntity <> (parent, headers);
        ResponseEntity <JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class); 

        return response.getBody();
    }
    @PostMapping("/createParentsUser")
    public String createNewParentUser(List<JsonNode> parentsList){
       
        String url = Properties.HOSTADDRESS.getProperties()+ "/target/user";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",Properties.TOKEN.getProperties());
        headers.set("Body", parentsList.toString());
        headers.setContentType(MediaType.APPLICATION_JSON);


        // send POST request
        HttpEntity<String> entity = new HttpEntity<>(parentsList.toString(), headers);

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
