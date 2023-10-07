package com.task.api.application;

import com.task.api.dto.ReservationDto;
import com.task.api.service.PartnerService;
import com.task.api.service.StoreService;
import com.task.domain.entity.Partner;
import com.task.domain.entity.Reservation;
import com.task.domain.type.ResType;
import com.task.noti.jwt.JwtAuthenticationProvider;
import com.task.noti.jwt.dto.TokenUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.task.api.dto.ReservationDto.ResponseEntity;
import static java.util.Collections.EMPTY_LIST;

@Service
@RequiredArgsConstructor
public class StoreApplication {

  private final StoreService storeService;
  private final PartnerService partnerService;
  private final JwtAuthenticationProvider provider;


  /**
   * 리뷰 수락이 필요로 하는 리뷰 목록을 가져오는 메소드
   * - getPartner : 토큰을 통한 파트너 정보를 가져옴
   * - getReservationList : 파트너에 해당하는 예약 목록을 가져옴
   * */
  public List<ReservationDto.ResponseEntity> getRservationList(String token) {
    TokenUser tokenUser = provider.getUserVo(token);
    Partner partner = partnerService.getPartner(tokenUser.getEmail());

    List<Reservation> reservations = storeService.getReservationList(partner)
        .getReservations()
        // stream 메서드를 통해서 REFUSE인 항목들만을 반환
        .stream()
        .filter(re -> re.getStatus().equals(ResType.REFUSE))
        .collect(Collectors.toList());

    if (!reservations.isEmpty()) {
      return reservations.stream()
          .map((Reservation reservation) -> new ResponseEntity(reservation.getReservationId(), reservation.getUser().getEmail(), reservation.getStatus()))
          .collect(Collectors.toList());
    } else {
      return EMPTY_LIST;
    }
  }
}
