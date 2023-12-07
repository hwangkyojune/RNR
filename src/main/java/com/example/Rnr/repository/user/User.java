package com.example.Rnr.repository.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints =
        {@UniqueConstraint(name="uniqueIdAndUserTypeId",
                columnNames = {"id","user_type_id"})})
public class User {
    @Id
    @Column(name="id",nullable = false,length = 36)
    private String id;
    @Column(name="pw",nullable = false,length = 20)
    private String pw;
    @Column(name="mail",nullable = false,length = 30)
    private String mail;
    @Column(name="birth_of_date",nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthOfDate;
    @Column(name="last_RNN",nullable=false,length = 7)
    private String lastRNN;
    @Column(name = "user_name",nullable = false,length = 4)
    private String userName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_type_id",referencedColumnName = "user_type_id")
    private UserType userType;

    public int getUserTypeId(){
        return userType.getUserTypeID();
    }
}
