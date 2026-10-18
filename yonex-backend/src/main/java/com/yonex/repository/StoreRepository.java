package com.yonex.repository;

import com.yonex.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByActiveTrue();
    List<Store> findByCityIgnoreCaseAndActiveTrue(String city);
}
