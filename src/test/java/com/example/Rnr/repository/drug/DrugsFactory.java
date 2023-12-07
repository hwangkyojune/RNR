package com.example.Rnr.repository.drug;

import com.example.Rnr.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DrugsFactory {
    public static List<Drug> createDrugs(){
        List<String> drugsName = List.of("프로포폴","졸피뎀","모르핀","헤로인","항히스타민");

        List<Drug> drugs = createDrugs(drugsName);

        return drugs;
    }

    public static List<Drug> createDrugs(List<String> drugsName){
        List<Drug> drugs = drugsName.stream()
                .map(DrugsFactory::createDrug)
                .collect(Collectors.toList());

        return drugs;
    }

    public static Drug createDrug(String drugName){
        Utility utility = new Utility();

        return Drug.builder()
                .drugName(drugName)
                .description(utility.createRandomString(10)).build();
    }
}
