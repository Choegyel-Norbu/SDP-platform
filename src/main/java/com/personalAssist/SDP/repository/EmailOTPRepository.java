package com.personalAssist.SDP.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personalAssist.SDP.model.EmailOTP;

@Repository
public interface EmailOTPRepository extends JpaRepository<EmailOTP, Long> {
    Optional<EmailOTP> findByEmailAndOtp(String email, String otp);
}
