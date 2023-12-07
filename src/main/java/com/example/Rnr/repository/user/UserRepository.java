package com.example.Rnr.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String>{
    public boolean existsById(String id);
}
