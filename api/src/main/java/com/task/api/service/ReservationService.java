package com.task.api.service;

import com.task.common.exception.CustomException;
import com.task.domain.entity.Reservation;
import com.task.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.task.common.exception.ErrorCode.*;
import static com.task.domain.type.ResType.REFUSE;
import static java.time.LocalDateTime.now;

@Service @RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;

  public Reservation saveUseReservation(Reservation reservation) {
    return reservationRepository.save(reservation);
  }
  public List<Reservation> getReviewByToken(String email) {
    return reservationRepository.getReservationByEmail(email);
  }

  @Transactional
  public Reservation getById(Long reservationId) {
    return reservationRepository.findById(reservationId)
        .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));
  }

  public void validReservation(String code, Reservation reservation) {
    if (!reservation.getStatus().equals(REFUSE)) {
      throw new CustomException(RESERVATION_NOT_FOUND);
    }

    if (!reservation.getReservationCode().equals(code)) {
      throw new CustomException(RESERVATION_NOT_VALID);
    }

    if (reservation.getReservationTime().isBefore(now())) {
      throw new CustomException(RESERVATION_NOT_TIME);
    }
  }

  @Transactional
  public void changeStatus(Long id) {
    Reservation reservation = reservationRepository.findById(id)
        .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));
    if (!reservation.getStatus().equals(REFUSE)) {
      throw new CustomException(RESERVATION_USE_STATUS);
    }else if (reservation.isReservationCheck()){
      throw new CustomException(RESERVATION_USE_STATUS);
    }

    reservation.changeSuccess();
  }

  public Reservation validStatus(Long reservationId) {
    Optional<Reservation> optionalReview =
        reservationRepository.findById(reservationId);

    if (!optionalReview.isPresent()) {
      throw new CustomException(RESERVATION_NOT_FOUND);
    }
    Reservation reservation = optionalReview.get();

    if (!reservation.isReservationCheck()) {
      throw new CustomException(RESERVATION_NOT_USE);
    }
    return reservation;
  }

  @Transactional
  public void deleteReservationByEntity(Reservation reservation) {
    reservationRepository.delete(reservation);
  }
}
