package com.example.Rnr.Member;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId;
    @Column(length = 200, nullable = false)
    private String password;
    private String name;

    private String role;


}
