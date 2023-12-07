package com.example.Rnr.repository.doctor;

import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReservedDoctorId implements Serializable {
    private String hospital;
    private String doctor;
}
