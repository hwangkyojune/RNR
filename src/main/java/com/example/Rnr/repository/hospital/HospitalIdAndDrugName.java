package com.example.Rnr.repository.hospital;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HospitalIdAndDrugName implements Serializable {
    private String hospital;

    private String drug;
}
