package com.algomania.onlinejudge.ProblemHub.DTO;

import java.util.List;

import com.algomania.onlinejudge.ProblemHub.Entity.SampleTestCase;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class ProblemDetailsDTO {

 int id;

 int    categoryId;
 int solutionId;

     String description;

  String constraints;

    

	
}
