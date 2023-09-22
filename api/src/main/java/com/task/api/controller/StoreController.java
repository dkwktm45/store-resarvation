package com.task.api.controller;

import com.task.api.dto.StoreDto;
import com.task.api.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
