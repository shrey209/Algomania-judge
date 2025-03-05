package com.skcoder.gate_way.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "users") // ✅ MongoDB Collection
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    private String id; // MongoDB uses String ObjectId

    private String username;
    private String userId;
    private String provider;
    private String email;
    private String role;
}
