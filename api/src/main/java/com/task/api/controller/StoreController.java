package com.task.api.controller;

import com.task.api.application.StoreApplication;
import com.task.api.dto.ReservationDto;
import com.task.api.dto.StoreDto;
import com.task.api.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservation/store")
public class StoreController {
  private final StoreService storeService;
  private final StoreApplication storeApplication;

  /**
   * 페이저블을 통한 매장 목록 요청
   *
   * feat : 페이저블에 대한 제약사항은 정의하지 않음
   * */
  @GetMapping("")
  public ResponseEntity<List<StoreDto>> getStoreListRequest(Pageable pageable) {
    return ResponseEntity.ok(storeService.getStoreList(pageable));
  }

  /**
   * 매장에 대한 예약거절(확정이 나지 않은) 목록을 요청
   * */
  @PostMapping("")
  public ResponseEntity<List<ReservationDto.ResponseEntity>> getReservationListRequest(
      @RequestHeader(name = "authorization") String token
  ) {
    return ResponseEntity.ok(storeApplication.getRservationList(token));
  }

  /**
   * 매장에 디테일 요청
   *
   * feat : storeName은 Unique key 값을 통한 요청
   * */
  @GetMapping("/detail")
  public ResponseEntity<StoreDto.Detail> detailRequest(
      @RequestParam String storeName
  ) {
    return ResponseEntity.ok(storeService.getDetail(storeName));
  }


}
