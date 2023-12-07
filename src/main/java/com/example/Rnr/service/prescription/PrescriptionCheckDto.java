package com.example.Rnr.service.prescription;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionCheckDto {
    private String drugName;
    private String patientId;
    private Date date;
    private int amount;
    private String doctorId;
    private String hospitalName;
}
