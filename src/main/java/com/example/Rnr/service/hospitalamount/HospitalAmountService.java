package com.example.Rnr.service.hospitalamount;

import com.example.Rnr.repository.hospital.HospitalAmount;
import com.example.Rnr.repository.hospital.HospitalAmountRepository;
import com.example.Rnr.repository.hospital.HospitalIdAndDrugName;
import com.example.Rnr.service.HospitalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HospitalAmountService {
    private final HospitalAmountRepository hospitalAmountRepository;

    public HospitalAmount findHospitalAmount(HospitalIdAndDrugName hospitalIdAndDrugName){
        Optional<HospitalAmount> optionalHospitalAmount = hospitalAmountRepository.findById(hospitalIdAndDrugName);

        if(optionalHospitalAmount.isEmpty()){
            return null;
        }

        return optionalHospitalAmount.get();
    }
    public List<HospitalAmount> getHospitalAmounts(String hospitalId){
        List<HospitalAmount> hospitalAmounts = hospitalAmountRepository
                .findByHospital_Id(hospitalId);

        return hospitalAmounts;
    }

    public List<DrugAmountDto> getDrugAmounts(String hospitalId){
        List<HospitalAmount> hospitalAmounts = getHospitalAmounts(hospitalId);

        List<DrugAmountDto> drugAmountDtos = hospitalAmounts.stream()
                .map(hospitalAmount -> DrugAmountDto.builder().drugName(hospitalAmount.getDrug().getDrugName())
                        .amount(hospitalAmount.getAmount()).build()).collect(Collectors.toList());

        return drugAmountDtos;
    }

    public boolean doHospitalHaveDrug(HospitalIdAndDrugName hospitalIdAndDrugName){
        return hospitalAmountRepository.existsById(hospitalIdAndDrugName);
    }

}
