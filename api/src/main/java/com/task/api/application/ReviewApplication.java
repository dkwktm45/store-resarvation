package com.task.api.application;

import com.task.api.dto.RequestWrite;
import com.task.api.dto.ReservationDto;
import com.task.api.service.ReservationService;
import com.task.api.service.ReviewService;
import com.task.api.service.StoreService;
import com.task.api.service.UserService;
import com.task.domain.entity.Reservation;
import com.task.domain.entity.Review;
import com.task.domain.entity.Store;
import com.task.domain.entity.User;
import com.task.noti.jwt.JwtAuthenticationProvider;
import com.task.noti.jwt.dto.TokenUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.task.api.dto.message.ResponseType.WRITE_SUCCESS_MESSAGE;

@Service
@RequiredArgsConstructor
public class ReviewApplication {

  private final UserService userService;
  private final StoreService storeService;
  private final ReservationService reservationService;
  private final ReviewService reviewService;
  private final JwtAuthenticationProvider provider;


  /**
   * 여러 리뷰 가져오기 메소드
   * - 매개변수 토큰을 통해서 해당 유저의 정보를 통해서 리뷰 목록을 반환한다.
   * */
  public List<ReservationDto.ResponseReview> getReviews(String token) {
    TokenUser tokenUser = provider.getUserVo(token);

    return reservationService.getReviewByToken(tokenUser.getEmail())
        .stream()
        .map(i -> new ReservationDto.ResponseReview(i.getReservationId(),i.getStore().getStoreName()))
        .collect(Collectors.toList());
  }
  /**
   * 리뷰 작성
   * - validStatus : 먼저 예약이 있고, 리뷰작성할 수 있는 상태인지를 검증
   * - deleteReservationByEntity : 삭제를 통해서 연관관계를 끊어버리는 역할
   * - getUserId , getNameStore : User, Store 객체를 가져온 다음 Review를 작성
   * */
  public String write(String token, RequestWrite requestWrite) {
    TokenUser tokenUser = provider.getUserVo(token);
    Reservation reservation = reservationService.getById(requestWrite.getReservationId());

    reservationService.validStatus(reservation);
    reservationService.deleteReservationByEntity(
        reservation
    );

    User user = userService.getUserId(tokenUser.getId());
    Store store = storeService.getStoreByStoreName(requestWrite.getStoreName());
    reviewService.createReview(
        Review.builder()
            .store(store)
            .user(user)
            .rating(requestWrite.getRating())
            .comment(requestWrite.getMessage())
            .build()
    );

    return WRITE_SUCCESS_MESSAGE.getMessage();
  }
}
