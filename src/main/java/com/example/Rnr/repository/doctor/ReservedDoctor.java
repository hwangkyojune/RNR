package com.example.Rnr.repository.doctor;

import com.example.Rnr.repository.hospital.Hospital;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ReservedDoctorId.class)
public class ReservedDoctor {
    @Id
    @JoinColumn(name="hospital_id",referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Hospital hospital;

    public void setHospital(Hospital hospital){
        this.hospital = hospital;

        if(!hospital.getReservedDoctors().contains(this)){
            hospital.addReservedDoctor(this);
        }
    }

    @Id
    @JoinColumn(name = "doctor_id",referencedColumnName = "id")
    @OneToOne(fetch = FetchType.EAGER)
    private Doctor doctor;

    public String getHospitalId(){
        return hospital.getId();
    }

    public String getDoctorId(){
        return doctor.getId();
    }
}
