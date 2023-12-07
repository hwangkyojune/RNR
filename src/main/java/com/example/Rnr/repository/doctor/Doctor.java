package com.example.Rnr.repository.doctor;

import com.example.Rnr.repository.prescription.Prescription;
import com.example.Rnr.repository.hospital.Hospital;
import com.example.Rnr.repository.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Doctor {
    @Id
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name="id",referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="hospital_id",referencedColumnName = "id")
    private Hospital hospital;

    public void setHospital(Hospital hospital){
        this.hospital = hospital;

        if(!hospital.getDoctors().contains(this)){
            hospital.addDoctor(this);
        }
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "doctor",cascade = CascadeType.ALL)
    @Builder.Default
    private List<Prescription> prescriptions = new ArrayList<>();

    public void addPrescription(Prescription prescription){
        prescriptions.add(prescription);

        if(prescription.getDoctor() != this){
            prescription.setDoctor(this);
        }
    }
}
