package com.example.Rnr.repository.hospital;

import com.example.Rnr.repository.drug.Drug;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@IdClass(HospitalIdAndDrugName.class)
public class HospitalAmount {
    public HospitalAmount(Hospital hospital,Drug drug,int amount){
        this.setHospital(hospital);
        this.setDrug(drug);
        this.setAmount(amount);
    }

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="hospital_id",referencedColumnName = "id")
    Hospital hospital;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="drug_name",referencedColumnName = "drug_name")
    Drug drug;

    private int amount;

    public void setDrug(Drug drug){
        this.drug = drug;

        if(!drug.getHospitalAmounts().contains(this)){
            drug.addHospitalAmount(this);
        }
    }
    public void setHospital(Hospital hospital){
        this.hospital = hospital;

        if(!hospital.getHospitalAmounts().contains(this)){
            hospital.addHospitalAmount(this);
        }
    }

    public void subtractAmount(int amountToSubtract){
        this.amount = this.amount-amountToSubtract;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        HospitalAmount that = (HospitalAmount) o;
        return getHospital() != null && Objects.equals(getHospital(), that.getHospital())
                && getDrug() != null && Objects.equals(getDrug(), that.getDrug());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(hospital, drug);
    }
}
