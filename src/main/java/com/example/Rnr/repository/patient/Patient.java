package com.example.Rnr.repository.patient;

import com.example.Rnr.repository.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Patient{
    @Id
    private String id;

    @OneToOne
    @MapsId(value = "id")
    @JoinColumn(name = "id",referencedColumnName = "id")
    private User user;

}
