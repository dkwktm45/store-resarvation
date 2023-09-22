package com.task.api.service;

import com.task.api.dto.StoreDto;
import com.task.common.exception.CustomException;
import com.task.common.exception.ErrorCode;
import com.task.domain.entity.Store;
import com.task.domain.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.task.common.exception.ErrorCode.STORE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class StoreService {

  private final StoreRepository storeRepository;

  public boolean nameExist(String storeName) {
    return storeRepository.existsByStoreName(storeName);
  }
  public Store getNameStore(String storeName) {
    return storeRepository.findByStoreName(storeName)
        .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
  }
  public List<StoreDto> getList(Pageable pageable) {
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

    return null;
  }

  public StoreDto.Detail getDetail(String storeName) {
    Store store = storeRepository.findByStoreName(storeName)
        .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

    int reviewSize = storeRepository.getReviewCountByStore(store);
    // todo 다른 방법을 찾아보자 -> 어차피 예약은 사라지니깐? 굳이 Entity로 만들어져야 하나?
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

  @Transactional
  public void saveStore(Store store) {
    storeRepository.save(store);
  }
}
