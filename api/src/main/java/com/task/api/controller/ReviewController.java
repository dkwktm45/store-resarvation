package com.task.api.controller;

import com.task.api.application.ReviewApplication;
import com.task.api.dto.ReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation/review")
public class ReviewController {
  private final ReviewApplication reviewApplication;
  /**
   * 리뷰 목록
   * */
  @GetMapping("")
  public ResponseEntity<List<ReservationDto.ResponseReview>> getReviewsRequest(
      @RequestHeader("authorization") String token
  ){
    return ResponseEntity.ok(reviewApplication.getReviews(token));
  }
}
