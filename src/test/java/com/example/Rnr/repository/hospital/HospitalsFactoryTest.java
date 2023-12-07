package com.example.Rnr.repository.hospital;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class HospitalsFactoryTest {
    @Test
    void 제대로된_hospital들을_생성하는지(){
        List<Hospital> hospitals = HospitalsFactory.createHospitals();

        List<String> ids = hospitals.stream().map(Hospital::getId).collect(Collectors.toList());

        System.out.println(Arrays.toString(ids.toArray()));
    }

}