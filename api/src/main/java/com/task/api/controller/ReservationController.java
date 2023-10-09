package com.task.api.controller;

import com.task.api.application.ReservationApplication;
import com.task.api.dto.ReservationDto;
import com.task.api.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reservation")
@RestController
@RequiredArgsConstructor
public class ReservationController {

  private final ReservationApplication reservationApplication;
  private final ReservationService reservationService;

  /**
   * 예약 요청
   * */
  @PostMapping("/available")
  public ResponseEntity<ReservationDto.Response> availableRequest(
      @RequestBody ReservationDto.Request req,
      @RequestHeader("authorization") String header
  ) {
    return ResponseEntity.ok(reservationApplication.sendReservationMessage(header,
        req));
  }

  /**
   * 예약승인 요청
   * */
  @PutMapping("/change")
  public ResponseEntity<Void> changeRequest(
      @RequestParam Long id
  ) {
    reservationApplication.changeStatus(id);
    return ResponseEntity.ok().build();
  }

  /**
   * Kiosk 도착 요청
   * */
  @GetMapping("/use")
  public ResponseEntity<String> useRequest(
      @RequestParam Long reservationId,
      @RequestParam String code
  ) {
    return ResponseEntity.ok(reservationApplication.useRequest(reservationId, code));
  }
}
