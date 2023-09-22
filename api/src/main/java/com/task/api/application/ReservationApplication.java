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

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationApplication {

  private final String MESSAGE = "예약이 원활하게 진행됐습니다.";
  private final ReservationService reservationService;
  private final UserService userService;
  private final StoreService storeService;

  /**
   * 만약 예약을 진행할 수 있다면 -> 잔여 좌석수가 남아 있다면
   * WATING아라는 Type으로 저장
   * */

  public ReservationDto.Response  sendReservationMessage(ReservationDto.Request req) {
    if (req.getCurrentSeat() < req.getMaxSeat()) {
      User user = userService.getUserId(req.getUserId());
      Store store = storeService.getNameStore(req.getStoreName());

      Reservation reservation =
          Reservation.builder()
              .user(user)
              .store(store)
              .status(ResType.WAITING)
              .store(store)
              .user(user)
              .reservationCode(UUID.randomUUID().toString())
              .reservationTime(LocalDateTime.now().plusHours(2))
              .build();
      Reservation response = reservationService.saveUseReservation(reservation);

      return ReservationDto.Response.builder()
          .storeName(store.getStoreName())
          .reservationId(response.getReservationId())
          .reservationCode(response.getReservationCode())
          .message(MESSAGE)
          .build();
    }
    return null;
  }


}
