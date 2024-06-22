package com.Algomania.users_microservice.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String id;  
    
     private String firstname;
     
    private String  lastname;


    private int hardCount;

    private int mediumCount;

    private int easyCount;

    private Set<Integer> solvedProblemsId;  

  private Set<Integer>todoproblems;
}
