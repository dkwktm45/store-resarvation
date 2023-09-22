package com.task.api.controller;

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

  @GetMapping("")
  public ResponseEntity<List<StoreDto>> getListRequest(Pageable pageable) {
    return ResponseEntity.ok(storeService.getList(pageable));
  }

  @GetMapping("/detail")
  public ResponseEntity<StoreDto.Detail> detailRequest(
      @RequestParam String storeName
  ) {
    return ResponseEntity.ok(storeService.getDetail(storeName));
  }


}
