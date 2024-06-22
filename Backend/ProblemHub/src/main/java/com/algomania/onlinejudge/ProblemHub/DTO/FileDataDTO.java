package com.algomania.onlinejudge.ProblemHub.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDataDTO {
	private String inputfiledataBase64;
    private String outputfiledataBase64;

    
}
