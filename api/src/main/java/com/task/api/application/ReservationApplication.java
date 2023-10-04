package com.task.api.application;

import com.task.api.dto.ReservationDto;
import com.task.api.service.ReservationService;
import com.task.api.service.StoreService;
import com.task.api.service.UserService;
import com.task.domain.entity.Reservation;
import com.task.domain.entity.Store;
import com.task.domain.entity.User;
import com.task.domain.type.ResType;
import com.task.redis.dto.MessageForm;
import com.task.redis.jwt.JwtAuthenticationProvider;
import com.task.redis.rabbitmq.senderType.SenderPartner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.task.api.dto.message.ResponseType.*;
import static com.task.redis.dto.MessageForm.Message.PARTNER_REFUSE;
import static com.task.redis.dto.MessageForm.Message.PARTNER_RES;

@Service
@RequiredArgsConstructor
public class ReservationApplication {

  private final ReservationService reservationService;
  private final UserService userService;
  private final JwtAuthenticationProvider provider;
  private final StoreService storeService;
  private final SenderPartner senderPartner;
  /**
   * 만약 예약을 진행할 수 있다면 -> 잔여 좌석수가 남아 있다면
   * WATING아라는 Type으로 저장
   */

  public ReservationDto.Response sendReservationMessage(String header, ReservationDto.Request req) {
    User user = userService.findByEmail(provider.getUserVo(header).getEmail());
    Store store = storeService.getNameStore(req.getStoreName());
    Reservation response;

    if (store.getTotalSeats() <= store.getAvailableSeats()) {
      response = getReservationAndSend(user, store, PARTNER_REFUSE);
    } else {
      response = getReservationAndSend(user, store, PARTNER_RES);
    }

    return responseByType(response);
  }

  private Reservation getReservationAndSend(User user, Store store, MessageForm.Message partnerRefuse) {
    Reservation response = reservationService.saveUseReservation(
        Reservation.createEntityAll(user, store, ResType.REFUSE)
    );

    senderPartner.sendMessage(new MessageForm(response.getReservationId(),
        store.getPartner().getPartnerEmail(), partnerRefuse)
    );
    return response;
  }

  @Transactional
  public String useRequest(Long reservationId, String code) {
    Reservation reservation = reservationService.getById(reservationId);
    reservationService.validReservation(code, reservation);

    Store reservationStore = reservation.getStore();
    if (reservationStore.getAvailableSeats() >= reservationStore.getTotalSeats()) {
      return ADMISSION_IS_IMPOSSIBLE.getMessage();
    } else {
      reservationStore.increaseUser();
      return ADMISSION_IS_POSSIBLE.getMessage();
    }
  }

  public void changeReservation(Long id) {
    reservationService.changeStatus(id);
  }

  private ReservationDto.Response responseByType(Reservation reservation) {

    if (Objects.equals(reservation.getStatus(), ResType.WAITING)) {
      return ReservationDto.Response.builder()
          .storeName(reservation.getStore().getStoreName())
          .reservationId(reservation.getReservationId())
          .reservationCode(reservation.getReservationCode())
          .message(SUCCESS_MESSAGE.getMessage())
          .build();
    }
    return ReservationDto.Response.builder()
        .storeName(reservation.getStore().getStoreName())
        .reservationId(reservation.getReservationId())
        .reservationCode(reservation.getReservationCode())
        .message(CONTINUE_MESSAGE.getMessage())
        .build();
  }
}
