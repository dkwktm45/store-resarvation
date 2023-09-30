package com.task.domain.repository;

import com.task.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {

  @Query("select r from Reservation r where r.user = (\n" +
      "    select u from User u where u.email = :email ) and\n" +
      "        r.reservationCheck = true")
  List<Reservation> getReservationByEmail(String email);
}
