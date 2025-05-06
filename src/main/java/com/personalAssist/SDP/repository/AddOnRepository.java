package com.personalAssist.SDP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.personalAssist.SDP.model.AddOn;

@Repository
public interface AddOnRepository extends JpaRepository<AddOn, Long>{

}
