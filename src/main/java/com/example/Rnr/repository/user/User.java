package com.example.Rnr.repository.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(uniqueConstraints =
        {@UniqueConstraint(name="uniqueIdAndUserTypeId",
                columnNames = {"id","user_type_id"})})
public class User {
    @Id
    @Column(nullable = false,length = 20)
    private String id;
    @Column(nullable = false,length = 20)
    private String pw;
    @Column(nullable = false,length = 30)
    private String mail;
    @Column(name="birth_of_date",nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthOfDate;
    @Column(name="last_RNN",nullable=false)
    private int lastRNN=0;
    @Column(name = "user_name",nullable = false,length = 4)
    private String userName;
    @Column(name="user_type_id",nullable = false)
    private int userTypeId;
}
