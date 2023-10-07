package com.task.api.service;

import com.task.domain.entity.Review;
import com.task.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReviewService {
  private final ReviewRepository reviewRepository;

  /**
   * 리뷰 생성
   * */
  @Transactional
  public void createReview(Review review) {
    reviewRepository.save(review);
  }
}
