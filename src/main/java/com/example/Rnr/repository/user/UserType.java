package com.example.Rnr.repository.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Check;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="usertype")
@Check(constraints = "user_type_id in (1,2)")
@Check(constraints = "role in ('patient','doctor')")
public class UserType {

    @Id
    @Column(name="user_type_id")
    private int userTypeID;
    @Column(name="role", nullable = false,length = 10)
    private String role;

    public static enum UserTypeProperty {
        PATIENT(1,"patient"),
        DOCTOR(2,"doctor");

        private int userTypeId;
        private String role;

        UserTypeProperty(int userTypeId,String role){
            this.userTypeId = userTypeId;
            this.role = role;
        }

        public int getUserTypeId(){
            return userTypeId;
        }

        public String getRole(){
            return role;
        }
    }

}
