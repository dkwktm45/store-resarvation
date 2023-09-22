package com.task.api.controller;

import com.task.common.jwt.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reservation")
@RestController
@RequiredArgsConstructor
public class ReservationController {

  private final RefreshTokenService refreshTokenService;


}
