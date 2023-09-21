package com.task.api.controller;

import com.task.api.dto.CreatePartner;
import com.task.api.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation/partner")
public class PartnerController {

  private final PartnerService partnerService;

  @PostMapping("/registration")
  public ResponseEntity<String> regPartner(
      @RequestBody CreatePartner.Request partnerRequest
  ) {
    return ResponseEntity.ok(partnerService.createPartner(partnerRequest));
  }
}
