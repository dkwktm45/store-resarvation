package com.task.api.application;

import com.task.api.dto.ReservationDto;
import com.task.api.service.ReservationService;
import com.task.api.service.StoreService;
import com.task.api.service.UserService;
import com.task.domain.entity.Reservation;
import com.task.domain.entity.Store;
import com.task.domain.entity.User;
import com.task.domain.type.ResType;
import com.task.redis.jwt.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.task.api.dto.message.ResponseType.*;

@Service
@RequiredArgsConstructor
public class ReservationApplication {

  private final ReservationService reservationService;
  private final UserService userService;
  private final JwtAuthenticationProvider provider;
  private final StoreService storeService;

  /**
   * 만약 예약을 진행할 수 있다면 -> 잔여 좌석수가 남아 있다면
   * WATING아라는 Type으로 저장
   * */

  public ReservationDto.Response  sendReservationMessage(String header, ReservationDto.Request req) {
    User user = userService.findByEmail(provider.getUserVo(header).getEmail());
    Store store = storeService.getNameStore(req.getStoreName());

    if (store.getTotalSeats() < store.getAvailableSeats()) {
      return responseByType(user, store, ResType.REFUSE);
    }else{
      return responseByType(user, store, ResType.WAITING);
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

  public void changeReservation(Long id) {
    reservationService.changeStatus(id);
  }
  private ReservationDto.Response responseByType(User user, Store store ,
                                                 ResType resType) {
    Reservation response = reservationService.saveUseReservation(
        Reservation.createEntityAll(user, store, resType)
    );

    if (Objects.equals(response.getStatus(),ResType.WAITING)) {
      return ReservationDto.Response.builder()
          .storeName(store.getStoreName())
          .reservationId(response.getReservationId())
          .reservationCode(response.getReservationCode())
          .message(SUCCESS_MESSAGE.getMessage())
          .build();
    }
    return ReservationDto.Response.builder()
        .storeName(store.getStoreName())
        .reservationId(response.getReservationId())
        .reservationCode(response.getReservationCode())
        .message(CONTINUE_MESSAGE.getMessage())
        .build();
  }
}
