package com.example.Rnr.repository.drug;

import com.example.Rnr.repository.prescription.Prescription;
import com.example.Rnr.repository.hospital.HospitalAmount;
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
public class Drug {
    @Id
    @Column(name="drug_name",length = 30)
    private String drugName;

    @Column(length = 100)
    private String description;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "drug")
    @Builder.Default
    private List<HospitalAmount> hospitalAmounts = new ArrayList<>();

    public void addHospitalAmount(HospitalAmount hospitalAmount){
        hospitalAmounts.add(hospitalAmount);

        if(hospitalAmount.getDrug()!=this){
            hospitalAmount.setDrug(this);
        }
    }

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "drug")
    @Builder.Default
    private List<Prescription> prescriptions = new ArrayList<>();

    public void addPrescription(Prescription prescription){
        prescriptions.add(prescription);

        if(prescription.getDrug()!=this){
            prescription.setDrug(this);
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Drug drug = (Drug) o;
        return getDrugName() != null && Objects.equals(getDrugName(), drug.getDrugName());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
