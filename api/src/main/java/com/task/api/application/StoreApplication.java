package com.task.api.application;

import com.task.api.dto.ReservationDto;
import com.task.api.service.PartnerService;
import com.task.api.service.StoreService;
import com.task.domain.entity.Partner;
import com.task.domain.entity.Reservation;
import com.task.domain.type.ResType;
import com.task.redis.jwt.JwtAuthenticationProvider;
import com.task.redis.jwt.dto.TokenUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.task.api.dto.ReservationDto.ResponseEntity;

@Service
@RequiredArgsConstructor
public class StoreApplication {

  private final StoreService storeService;
  private final PartnerService partnerService;
  private final JwtAuthenticationProvider provider;


  public List<ReservationDto.ResponseEntity> getRservationList(String token) {
    TokenUser tokenUser = provider.getUserVo(token);
    Partner partner = partnerService.getPartner(tokenUser.getEmail());

    List<Reservation> reservations = storeService.getReservationList(partner)
        .getReservations()
        .stream()
        .filter(re -> re.getStatus().equals(ResType.REFUSE))
        .collect(Collectors.toList());

    if (!reservations.isEmpty()) {
      List<ReservationDto.ResponseEntity> collect = reservations.stream()
          .map((Reservation reservation) -> new ResponseEntity(reservation.getReservationId(), reservation.getUser().getEmail(), reservation.getStatus()))
          .collect(Collectors.toList());
      return collect;
    } else {
      return null;
    }
  }
}
