package com.task.api.service;

import com.task.api.dto.StoreDto;
import com.task.common.exception.CustomException;
import com.task.common.exception.ErrorCode;
import com.task.domain.entity.Partner;
import com.task.domain.entity.Store;
import com.task.domain.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.task.common.exception.ErrorCode.STORE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class StoreService {

  private final StoreRepository storeRepository;

  /**
   * 매장이 존재하는지 여부를 묻는 메소드
   * */
  public boolean nameExist(String storeName) {
    return storeRepository.existsByStoreName(storeName);
  }

  /**
   * storeName 해당하는 매장을 반환하는 메소드
   * */
  public Store getStoreByStoreName(String storeName) {
    return storeRepository.findByStoreName(storeName)
        .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
  }

  /**
   * Pageable 매개변수를 통한 매장 목록을 반환*/
  public List<StoreDto> getStoreList(Pageable pageable) {
    List<Store> stores = storeRepository.findAll(pageable).getContent();
    if (!stores.isEmpty()) {
      return stores.stream().map(store ->
              StoreDto.builder()
                  .storeDescription(store.getStoreDescription())
                  .storeName(store.getStoreName())
                  .storeLocation(store.getStoreLocation())
                  .availableSeats(store.getAvailableSeats())
                  .totalSeats(store.getTotalSeats())
                  .build())
          .collect(Collectors.toList());
    }
    return Collections.EMPTY_LIST;
  }
  /**
   * Partner 매개변수에 해당하는 매장을 반환하는 메소드
   * */
  public Store getReservationList(Partner partner) {
    return storeRepository.findByPartner(partner)
        .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));
  }
  /**
   * 매장의 세부사항 메소드
   * - getReviewCountByStore : store에 대한 review 사이즈를 반환 (커스텀 메소드 사용)
   * - getReservationCountByStore : 예약확정이 된 상태의 값들을 가져온다.
   *
   * feat
   * - 예약확정 : reservationCheck가 true인 경우 , 키오스크에서 도착한 경우
   * */
  public StoreDto.Detail getDetail(String storeName) {
    Store store = storeRepository.findByStoreName(storeName)
        .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

    int reviewSize = storeRepository.getReviewCountByStore(store);
    int currentReservation = storeRepository.getReservationCountByStore(store);

    return StoreDto.Detail.builder()
        .reviewSize(reviewSize)
        .availableSeats(store.getAvailableSeats())
        .reservationsSize(currentReservation)
        .totalSeats(store.getTotalSeats())
        .storeName(storeName)
        .storeDescription(store.getStoreDescription())
        .storeLocation(store.getStoreLocation())
        .build();
  }

  /**
   * 매장 저장 메소드
   * */
  @Transactional
  public void saveStore(Store store) {
    storeRepository.save(store);
  }
}
