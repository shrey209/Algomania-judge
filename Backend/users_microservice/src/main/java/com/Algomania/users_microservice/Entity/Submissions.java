package com.Algomania.users_microservice.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Document(collection = "Submissions")
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class Submissions {

    @Id
    String id;
    
    int problemid;
    
    String userId;
    
    String lang;
    
    String code;
    
    String status;
    
    Date date;
}
