package com.example.Rnr.repository.hospital;

import com.example.Rnr.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class HospitalsFactory {

    public static List<Hospital> createHospitals(){
        List<String> ids = List.of("shane5969","ddd55","hgo55","lac184","cj487221");

        List<Hospital> hospitals = createHospitals(ids);

        return hospitals;
    }

    public static List<Hospital> createHospitals(List<String> ids){
        List<Hospital> hospitals = ids.stream()
                .map(HospitalsFactory::createHospital)
                .collect(Collectors.toList());

        return hospitals;
    }

    public static Hospital createHospital(String HospitalId){
        Utility utility = new Utility();

        return Hospital.builder().id(HospitalId)
                .pw(utility.createRandomString(5))
                .name(utility.createRandomString(5))
                .contactInformation(utility.createRandomString(5))
                .build();

    }
    public static Hospital createHospitalRandomly(){
        String id = UUID.randomUUID().toString();
        return createHospital(id);
    }

    public static List<Hospital> createHospitalsAsBatch(int num){
        List<Hospital> hospitals = new ArrayList<>();
        for(int i=0;i<num;i++){
            hospitals.add(createHospitalRandomly());
        }

        return hospitals;
    }
}
