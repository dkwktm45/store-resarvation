package com.task.api.application;

import com.task.api.dto.ReservationDto;
import com.task.api.service.ReservationService;
import com.task.api.service.ReviewService;
import com.task.domain.entity.Reservation;
import com.task.redis.jwt.JwtAuthenticationProvider;
import com.task.redis.jwt.dto.TokenUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewApplication {

  private final ReservationService reservationService;
  private final ReviewService reviewService;
  private final JwtAuthenticationProvider provider;

  public List<ReservationDto.ResponseReview> getReviews(String token) {
    TokenUser tokenUser = provider.getUserVo(token);
    List<Reservation> reviews =
        reservationService.getReviewByToken(tokenUser.getEmail());

    return reviews.stream()
        .map(i -> new ReservationDto.ResponseReview(i.getReservationId(),i.getStore().getStoreName()))
        .collect(Collectors.toList());
  }
}
