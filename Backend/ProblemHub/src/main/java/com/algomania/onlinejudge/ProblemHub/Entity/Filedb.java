package com.algomania.onlinejudge.ProblemHub.Entity;





import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Algomania_Files")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Filedb {
	
	@jakarta.persistence.Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	  private int id;

	private int problemDetailsId;
	
	private String inputfileName;
	
    private	String outputFileName;
	
	@jakarta.persistence.Lob
	  private byte[] inputfiledata;
	
	@jakarta.persistence.Lob
	  private byte[] outputfiledata;
}
