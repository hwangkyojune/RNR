package com.example.Rnr.repository.prescription;

import lombok.*;

import java.io.Serializable;
import java.util.Date;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PrescriptionId implements Serializable {
    private String patient;
    private String drug;
    private Date prescriptionTimeStamp;
}
