package com.unqiuehire.kashflow.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiskResultResponseDto {

    private int score;
    private String category;
    private Map<String, Double> factors;
    private List<String> recommendations;
}
