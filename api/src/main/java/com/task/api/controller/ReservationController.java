package com.task.api.controller;

import com.task.api.application.ReservationApplication;
import com.task.api.dto.ReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reservation")
@RestController
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationApplication reservationApplication;

  /**
   * 예약을 할때 남은 좌석 수와 함께 유요한 값을 가져온다.
   * 정책 1. 남은 좌석 수가있다면 예약이 가능하다. 단, 예약이 없어야함 ResType이 DONE이어야 함!!
   * 정책 2. 현재 대기자 명단이 있을 경우 부터 시작해서 점주는 거절을 할 수 있다.
   * */
  @PostMapping("/available")
  public ResponseEntity<ReservationDto.Response> availableRequest(
      @RequestBody ReservationDto.Request req,
      @RequestHeader("authorization") String header
  ) {
    return ResponseEntity.ok(reservationApplication.sendReservationMessage(header,
        req));
  }
  @PutMapping("/change")
  public ResponseEntity<Void> changeRequest(
      @RequestParam Long id
  ) {
    reservationApplication.changeReservation(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/use")
  public ResponseEntity<String> useRequest(
      @RequestParam Long reservationId,
      @RequestParam String code
  ) {
    return ResponseEntity.ok(reservationApplication.useRequest(reservationId, code));
  }
}
