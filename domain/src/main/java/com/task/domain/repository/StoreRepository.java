package com.task.domain.repository;

import com.task.domain.entity.Partner;
import com.task.domain.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store,Long> {
  boolean existsByStoreName(String storeName);

  Page<Store> findAll(Pageable pageable);

  Optional<Store> findByStoreName(String storeName);

  @Query("SELECT COUNT(r) FROM Review r WHERE r.store = :store")
  int getReviewCountByStore(@Param("store") Store store);

  @Query("SELECT COUNT(r) FROM Reservation r WHERE r.store = :store")
  int getReservationCountByStore(@Param("store") Store store);

  Optional<Store> findByPartner(Partner partner);
}
