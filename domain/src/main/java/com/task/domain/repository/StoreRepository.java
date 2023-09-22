package com.task.domain.repository;

import com.task.domain.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store,Long> {
  boolean existsByStoreName(String storeName);

  Page<Store> findAll(Pageable pageable);
}
