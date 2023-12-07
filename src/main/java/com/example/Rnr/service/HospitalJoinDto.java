package com.example.Rnr.service;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HospitalJoinDto {
    private String id;
    private String pw;
    private String pwCheck;
    private String name;
    private String contactInfo;
}
