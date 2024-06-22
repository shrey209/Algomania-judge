package com.Algomania.users_microservice.Entity;



import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubmissionInfo{
    private String status;
    private Date date;
    private String lang;
}