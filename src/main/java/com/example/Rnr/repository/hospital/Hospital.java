package com.example.Rnr.repository.hospital;

import com.example.Rnr.repository.doctor.Doctor;
import com.example.Rnr.repository.doctor.ReservedDoctor;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hospital {
    @Id
    @Column(nullable = false,length = 36)
    private String id;
    @Column(nullable = false,length = 20)
    private String pw;
    @Column(nullable = false,length = 15)
    private String name;
    @Column(nullable = false,length = 15)
    private String contactInformation;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "hospital")
    private List<Doctor> doctors = new ArrayList<>();

    public void addDoctor(Doctor doctor){
        doctors.add(doctor);

        if(doctor.getHospital()!=this){
            doctor.setHospital(this);
        }
    }

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "hospital")
    @Builder.Default
    private List<ReservedDoctor> reservedDoctors = new ArrayList<>();

    public void addReservedDoctor(ReservedDoctor reservedDoctor){
        reservedDoctors.add(reservedDoctor);

        if(reservedDoctor.getHospital()!=this){
            reservedDoctor.setHospital(this);
        }
    }


    @OneToMany(fetch = FetchType.LAZY,mappedBy="hospital")
    @Builder.Default
    private List<HospitalAmount> hospitalAmounts=new ArrayList<>();


    public void addHospitalAmount(HospitalAmount hospitalAmount){
        hospitalAmounts.add(hospitalAmount);

        if(hospitalAmount.getHospital()!=this){
            hospitalAmount.setHospital(this);
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Hospital hospital = (Hospital) o;
        return getId() != null && Objects.equals(getId(), hospital.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
