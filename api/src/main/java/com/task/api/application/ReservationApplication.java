package com.task.api.application;

import com.task.api.dto.ReservationDto;
import com.task.api.service.ReservationService;
import com.task.api.service.StoreService;
import com.task.api.service.UserService;
import com.task.domain.entity.Reservation;
import com.task.domain.entity.Store;
import com.task.domain.entity.User;
import com.task.domain.type.ResType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.task.api.dto.message.ResponseType.*;

@Service
@RequiredArgsConstructor
public class ReservationApplication {

  private final ReservationService reservationService;
  private final UserService userService;
  private final StoreService storeService;

  /**
   * 만약 예약을 진행할 수 있다면 -> 잔여 좌석수가 남아 있다면
   * WATING아라는 Type으로 저장
   * */

  public ReservationDto.Response  sendReservationMessage(ReservationDto.Request req) {
    User user = userService.getUserId(req.getUserId());
    Store store = storeService.getNameStore(req.getStoreName());


    if (req.getCurrentSeat() < req.getMaxSeat()) {
      Reservation response = reservationService.saveUseReservation(
          Reservation.createEntityAll(user, store, ResType.WAITING)
      );

      return ReservationDto.Response.builder()
          .storeName(store.getStoreName())
          .reservationId(response.getReservationId())
          .reservationCode(response.getReservationCode())
          .message(SUCCESS_MESSAGE.getMessage())
          .build();
    }else{
      Reservation response = reservationService.saveUseReservation(
          Reservation.createEntityAll(user, store, ResType.REFUSE)
      );

      return ReservationDto.Response.builder()
          .storeName(store.getStoreName())
          .reservationId(response.getReservationId())
          .reservationCode(response.getReservationCode())
          .message(CONTINUE_MESSAGE.getMessage())
          .build();
    }
  }
  @Transactional
  public String useRequest(Long reservationId, String code) {
    Reservation reservation = reservationService.validCode(reservationId,code);
    Store reservationStore = reservation.getStore();
    if (reservationStore.getAvailableSeats() >= reservationStore.getTotalSeats()) {
      return ADMISSION_IS_IMPOSSIBLE.getMessage();
    }else{
      reservationStore.increaseUser();
      return ADMISSION_IS_POSSIBLE.getMessage();
    }
  }
}
