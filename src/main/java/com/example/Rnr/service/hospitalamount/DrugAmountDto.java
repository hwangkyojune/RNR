package com.example.Rnr.service.hospitalamount;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DrugAmountDto {
    private String drugName;
    private int amount;
}
