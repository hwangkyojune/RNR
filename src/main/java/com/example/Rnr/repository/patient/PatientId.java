package com.example.Rnr.repository.patient;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class PatientId implements Serializable {
    @Column(name="id")
    private String id;

    @Column(name="user_type_id")
    private String userTypeId;

    public PatientId(String id, String userTypeId) {
        this.id = id;
        this.userTypeId = userTypeId;
    }
}
