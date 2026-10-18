package com.yonex.repository;

import com.yonex.entity.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {
    List<Athlete> findByActiveTrue();
    List<Athlete> findBySportAndActiveTrue(String sport);
}
