package com.example.Rnr.service;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinDto {
    private String id;
    private String pw;
    private String pwCheck;
    private String mail;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date birthOfDate;
    private String lastRNN;
    private String userName;
    private int userTypeId;
}
