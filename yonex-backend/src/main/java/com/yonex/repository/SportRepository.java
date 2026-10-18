package com.yonex.repository;

import com.yonex.entity.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SportRepository extends JpaRepository<Sport, Long> {
    Optional<Sport> findBySlug(String slug);
    List<Sport> findByActiveTrue();
    boolean existsBySlug(String slug);
}
