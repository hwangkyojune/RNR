package com.example.Rnr.repository.patient;

import com.example.Rnr.repository.prescription.Prescription;
import com.example.Rnr.repository.user.User;
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
public class Patient{
    @Id
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id",referencedColumnName = "id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "patient",cascade = CascadeType.ALL)
    @Builder.Default
    List<Prescription> prescriptions = new ArrayList<>();

    public void addPrescription(Prescription prescription){
        prescriptions.add(prescription);

        Patient patient = prescription.getPatient();

        if(patient!=this){
            prescription.setPatient(this);
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Patient patient = (Patient) o;
        return getId() != null && Objects.equals(getId(), patient.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
