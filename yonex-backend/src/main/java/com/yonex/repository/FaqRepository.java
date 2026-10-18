package com.yonex.repository;

import com.yonex.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Long> {
    List<Faq> findByActiveTrueOrderByDisplayOrderAsc();
    List<Faq> findByCategoryIgnoreCaseAndActiveTrueOrderByDisplayOrderAsc(String category);
}
