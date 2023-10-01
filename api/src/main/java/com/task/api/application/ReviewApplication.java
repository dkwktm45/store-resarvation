package com.task.api.application;

import com.task.api.dto.RequestWrite;
import com.task.api.dto.ReservationDto;
import com.task.api.service.ReservationService;
import com.task.api.service.ReviewService;
import com.task.api.service.StoreService;
import com.task.api.service.UserService;
import com.task.domain.entity.Review;
import com.task.domain.entity.Store;
import com.task.domain.entity.User;
import com.task.redis.jwt.JwtAuthenticationProvider;
import com.task.redis.jwt.dto.TokenUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewApplication {

  private final UserService userService;
  private final StoreService storeService;
  private final ReservationService reservationService;
  private final ReviewService reviewService;
  private final JwtAuthenticationProvider provider;

  public List<ReservationDto.ResponseReview> getReviews(String token) {
    TokenUser tokenUser = provider.getUserVo(token);

    return reservationService.getReviewByToken(tokenUser.getEmail())
        .stream()
        .map(i -> new ReservationDto.ResponseReview(i.getReservationId(),i.getStore().getStoreName()))
        .collect(Collectors.toList());
  }

  public String write(String token, RequestWrite requestWrite) {
    TokenUser tokenUser = provider.getUserVo(token);
    reservationService.deleteReservationByEntity(
        reservationService.validStatus(requestWrite.getReservationId())
    );

    User user = userService.getUserId(tokenUser.getId());
    Store store = storeService.getNameStore(requestWrite.getStoreName());
    reviewService.createReview(
        Review.builder()
            .store(store)
            .user(user)
            .rating(requestWrite.getRating())
            .comment(requestWrite.getMessage())
            .build()
    );

    return null;
  }
}
