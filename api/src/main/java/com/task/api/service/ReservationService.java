package com.task.api.service;

import com.task.common.exception.CustomException;
import com.task.domain.entity.Reservation;
import com.task.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.task.common.exception.ErrorCode.*;
import static com.task.domain.type.ResType.REFUSE;
import static java.time.LocalDateTime.now;

@Service @RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;

  public Reservation saveUseReservation(Reservation reservation) {
    return reservationRepository.save(reservation);
  }

  @Transactional
  public Reservation validCode(Long reservationId, String code) {
     Reservation reservation = reservationRepository.findById(reservationId)
        .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));

    if (!reservation.getStatus().equals(REFUSE)) {
      throw new CustomException(RESERVATION_NOT_FOUND);
    }

    if (!reservation.getReservationCode().equals(code)) {
      throw new CustomException(RESERVATION_NOT_VALID);
    }

    if (reservation.getReservationTime().isBefore(now())) {
      throw new CustomException(RESERVATION_NOT_TIME);
    }
    return reservation;
  }

  @Transactional
  public void changeStatus(Long id) {
    Reservation reservation = reservationRepository.findById(id)
        .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));
    if (reservation.getStatus().equals(REFUSE)) {
      reservation.changeSuccess();
    }
  }
}
