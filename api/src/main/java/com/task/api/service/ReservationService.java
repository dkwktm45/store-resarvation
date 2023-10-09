package com.task.api.service;

import com.task.common.exception.CustomException;
import com.task.domain.entity.Reservation;
import com.task.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.task.common.exception.ErrorCode.*;
import static com.task.domain.type.ResType.REFUSE;
import static java.time.LocalDateTime.now;

@Service @RequiredArgsConstructor
public class ReservationService {

  private final ReservationRepository reservationRepository;

  /**
   * Reservation 저장 메소드
   * */
  @Transactional
  public Reservation saveUseReservation(Reservation reservation) {
    return reservationRepository.save(reservation);
  }

  /**
   * 이메일을 통해서 예약 목록을 가져온다.
   * - getReservationByEmail 메소드는 커스텀 Query를 사용해서
   * 특정 ReservationCheck(키오스크를 통해서 고객이 들어왔을 경우)가 true 경우
   *
   * feat : 저 같은 경우 키오스크를 통해서 입장이 되었을 경우 되도록 구현을 하였습니다.
   * */
  public List<Reservation> getReviewByToken(String email) {
    return reservationRepository.getReservationByEmail(email);
  }

  /**
   * reservationId 매개변수를 통해서 해당 Object를 반환하는 메소드
   * */
  @Transactional
  public Reservation getById(Long reservationId) {
    return reservationRepository.findById(reservationId)
        .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));
  }

  /**
   * 해당 예약이 적절한지를 판단하는 메소드
   * */
  public void validReservation(String code, Reservation reservation) {
    // 수락된 예약인지를 판단
    if (!reservation.getStatus().equals(REFUSE)) {
      throw new CustomException(RESERVATION_NOT_FOUND);
    }
    // code가 일치한지를 판단
    if (!reservation.getReservationCode().equals(code)) {
      throw new CustomException(RESERVATION_NOT_VALID);
    }

    // 규정 내의 시간안에 도착하는지를 판단
    if (reservation.getReservationTime().isBefore(now())) {
      throw new CustomException(RESERVATION_NOT_TIME);
    }
  }



  /**
   * 리뷰를 작성할 수 있는지를 판단하는 메소드 
   * */
  public void validStatus(Reservation reservation) {
    if (!reservation.isReservationCheck()) {
      throw new CustomException(RESERVATION_NOT_USE);
    }
  }

  /**
   * Reservation 매개변수를 통한 예약 삭제
   * */
  @Transactional
  public void deleteReservationByEntity(Reservation reservation) {
    reservationRepository.delete(reservation);
  }
}
