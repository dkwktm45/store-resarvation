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
   * */
  @GetMapping("")
  public ResponseEntity<List<StoreDto>> getStoreListRequest(Pageable pageable) {
    return ResponseEntity.ok(storeService.getStoreList(pageable));
  }

  @PostMapping("")
  public ResponseEntity<List<ReservationDto.ResponseEntity>> getReservationListRequest(
      @RequestHeader(name = "authorization") String token
  ) {
    return ResponseEntity.ok(storeApplication.getRservationList(token));
  }
  @GetMapping("/detail")
  public ResponseEntity<StoreDto.Detail> detailRequest(
      @RequestParam String storeName
  ) {
    return ResponseEntity.ok(storeService.getDetail(storeName));
  }


}
