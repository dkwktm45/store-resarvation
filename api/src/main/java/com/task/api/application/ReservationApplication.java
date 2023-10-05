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
   * 남은 좌석수를 판단하고 예약을 진행하는 메소드
   * - getReservationAndSend : 예약 진행현황을 반환하고, 점주에게 알림이 울리도록 하는 메소드
   *
   * feat : store의 총자리(totalSeats) , 이용가능(availableSeats) 좌석 를 판단하고 그에 맞는
   * 메소드를 수행하도록 규정
   */
  public ReservationDto.Response sendReservationMessage(String header, ReservationDto.Request req) {
    User user = userService.findByEmail(provider.getUserVo(header).getEmail());
    Store store = storeService.getStoreByStoreName(req.getStoreName());
    Reservation response;

    if (store.getTotalSeats() <= store.getAvailableSeats()) {
      response = getReservationAndSend(user, store, PARTNER_REFUSE);
    } else {
      response = getReservationAndSend(user, store, PARTNER_RES);
    }

    return responseByType(response);
  }

  /**
   * 예약을 각각의 매개변수를 통해 저장하고, 점주에게 알림이 가는 메소드
   * - saveUseReservation : user, store, ResType.REFUSE 를 통한 Reservation 객체 저장
   * - sendMessage : getPartnerEmail에게 알림이 가는 역할
   * */
  private Reservation getReservationAndSend(User user, Store store, MessageForm.Message partnerRefuse) {
    Reservation response = reservationService.saveUseReservation(
        Reservation.createEntityAll(user, store, ResType.REFUSE)
    );

    senderPartner.sendMessage(new MessageForm(response.getReservationId(),
        store.getPartner().getPartnerEmail(), partnerRefuse)
    );
    return response;
  }

  /**
   * 지금 바로 입장 가능한지에 대한 정보를 반환하는 메소드
   * - validReservation : 타탕한 예약 코드인지를 묻는 메소드
   *
   * feat : 입장 가능한지는 좌석을 보고 결정하도록 규정
   * */
  @Transactional
  public String useRequest(Long reservationId, String code) {
    Reservation reservation = reservationService.getById(reservationId);
    reservationService.validReservation(code, reservation);

    // 키오스크 도착정보 변경
    reservation.changeKiosk();

    Store reservationStore = reservation.getStore();
    if (reservationStore.getAvailableSeats() >= reservationStore.getTotalSeats()) {
      return ADMISSION_IS_IMPOSSIBLE.getMessage();
    } else {
      reservationStore.increaseUser();
      return ADMISSION_IS_POSSIBLE.getMessage();
    }
  }

  /**
   * ResType 유형에 따른 Entity를 DTO로 변환하는 메소드*/
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
