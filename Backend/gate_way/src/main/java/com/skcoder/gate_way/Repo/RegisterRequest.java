package com.skcoder.gate_way.Repo;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RegisterRequest {
    private String username;
    private String password;
   private String gmail;
}
