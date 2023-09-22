package com.task.api.service;

import com.task.domain.entity.Reservation;
import com.task.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;

  public Reservation saveUseReservation(Reservation reservation) {
    return reservationRepository.save(reservation);
  }
}
