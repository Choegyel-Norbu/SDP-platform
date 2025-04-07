package com.personalAssist.SDP.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personalAssist.SDP.model.User;
import com.personalAssist.SDP.util.RoleType;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String email);
    
    boolean existsByEmail(String email);

}
