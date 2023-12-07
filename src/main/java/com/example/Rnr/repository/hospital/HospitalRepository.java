package com.example.Rnr.repository.hospital;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital,String> {
    List<Hospital> findByName(String name);

    public boolean existsById(String id);
}
