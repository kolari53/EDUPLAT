package com.CodeShake.Edushake.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.CodeShake.Edushake.Properties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class MainController {
    @GetMapping("/api/sync/deniz")
    public String syncPerson(){
        ParentsController parentsController = new ParentsController();
        TeachersController teachersController = new TeachersController();
        StudentsController studentsController = new StudentsController();
        
        JsonNode studentsList = studentsController.getStudents();
        JsonNode teachersList = teachersController.getTeachers();
        JsonNode userList =getUsers();


        String federationId;
        JsonNode parentJson;

        List<JsonNode> studentNodes = new ArrayList<JsonNode>();
        List<JsonNode> teacherNodes = new ArrayList<JsonNode>();
        List<JsonNode> parentNodes  = new ArrayList<JsonNode>();

        List<String> users = new ArrayList<String>();

        for(int i = 0 ; i < studentsList.size() ; i++){
            federationId = getFederationId(studentsList.get(i).get("id").asText(), studentsList.get(i));
            ((ObjectNode) studentsList.get(i)).put("federationId",federationId);
            ((ObjectNode) studentsList.get(i)).put("role","STUDENT");
            ((ObjectNode) studentsList.get(i)).remove("parentId");
            studentNodes.add(studentsList.get(i));
        }
        for(int i = 0 ; i < studentsList.size() ; i ++){
            federationId = getFederationId(studentsList.get(i).get("id").asText(), studentsList.get(i));
            parentJson = parentsController.getParentById(studentsController.getStudents().get(i).get("parentId").asText());
            ((ObjectNode) parentJson).put("federationId",federationId);
            ((ObjectNode) parentJson).put("role","PARENT");
            parentNodes.add(parentJson);
        }
        for(int i = 0 ; i < teachersList.size() ; i++){
            federationId =  getFederationId(teachersList.get(i).get("id").asText(), teachersList.get(i));
            ((ObjectNode) teachersList.get(i)).put("federationId",federationId);
            ((ObjectNode) teachersList.get(i)).put("role","TEACHER");
            teacherNodes.add(teachersList.get(i));
        }
        studentsController.createNewStudentUser(studentNodes);
        parentsController.createNewParentUser(parentNodes);
        teachersController.createNewTeacherUser(teacherNodes);

        for(int i = 0 ; i < userList.size() ; i++){
            users.add(userList.get(i).get("id").asText());
        }
        //deleteAllUser(users);
        return "Case is complated.";
    }

    @GetMapping("/getUsers")
    public JsonNode getUsers(){
        String url = Properties.HOSTADDRESS.getProperties() + "/target/user";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",Properties.TOKEN.getProperties());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String studentsUsers = restTemplate.getForObject(url, String.class, headers);

        HttpEntity<String> entity = new HttpEntity<>(studentsUsers, headers);
        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);

        return Arrays.asList(response.getBody()).get(0);
    }


    @DeleteMapping("/HOSTADDRESS/sync/deleteUser")
    public String deleteAllUser(List<String> userIds){
        String url = Properties.HOSTADDRESS.getProperties() + "/target/user/?userIds=";
        for(int i = 0 ; i < userIds.size() ; i++){
            if(i == 0){
                url = url + userIds.get(i);
            }else{
                url = url + "," + userIds.get(i);
            }
        }
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",Properties.TOKEN.getProperties());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String FederationId = restTemplate.getForObject(url, String.class, headers);

        HttpEntity<String> entity = new HttpEntity<>(FederationId, headers);

        restTemplate.exchange(url, HttpMethod.DELETE, entity, String.class);
        return "Process is done"; 
    }

    @PostMapping("/usersFedID/{id}")
    public String getFederationId(@PathVariable(name = "id") String id,JsonNode studentsBody) {
        String url = Properties.HOSTADDRESS.getProperties() + "/federation/registration/" + id;
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",Properties.TOKEN.getProperties());
        headers.set("Body", studentsBody.toString());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String FederationId = restTemplate.getForObject(url, String.class, headers);

        HttpEntity<String> entity = new HttpEntity<>(FederationId, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return response.getBody().toString();
    }
}
