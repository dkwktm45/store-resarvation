package com.task.api.service;

import com.task.api.dto.StoreDto;
import com.task.domain.entity.Store;
import com.task.domain.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

  private final StoreRepository storeRepository;

  public boolean nameExist(String storeName) {
    return storeRepository.existsByStoreName(storeName);
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

}
