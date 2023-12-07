package com.example.Rnr.service.prescription;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionDto {
    private String patientId;
    private String patientPw;
    private String drugName;
    private int amount;
}
