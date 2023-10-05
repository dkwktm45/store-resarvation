package com.task.api.controller;

import com.task.api.application.ReviewApplication;
import com.task.api.dto.RequestWrite;
import com.task.api.dto.ReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation/review")
public class ReviewController {
  private final ReviewApplication reviewApplication;
  /**
   * 리뷰 목록 요청
   * */
  @GetMapping("")
  public ResponseEntity<List<ReservationDto.ResponseReview>> getReviewsRequest(
      @RequestHeader("authorization") String token
  ){
    return ResponseEntity.ok(reviewApplication.getReviews(token));
  }

  /**
   * 리뷰 작성 요청
   * */
  @PostMapping("/wirte")
  public ResponseEntity<String> writeRequest(
      @RequestHeader("authorization") String token,
      @RequestBody RequestWrite requestWrite
      ){
    return ResponseEntity.ok(reviewApplication.write(token, requestWrite));
  }
}
