package com.task.api.application;

import com.task.api.dto.CreatePartner;
import com.task.api.dto.StoreDto;
import com.task.api.service.PartnerService;
import com.task.common.exception.CustomException;
import com.task.domain.entity.Partner;
import com.task.domain.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.task.common.exception.ErrorCode.STORE_EXIST;

@Service
@RequiredArgsConstructor
public class PartnerApplication {
  private final PartnerService partnerService;
  private final com.task.api.service.StoreService storeService;

  /**
   * 스토어 등록 메소드
   * - nameExist : 이미 등록된 매장인지를 검증
   * */
  @Transactional
  public Store registStore(CreatePartner.Store req) {
    Partner partner = partnerService.getPartner(req.getEmail());

    StoreDto storeDto = req.getStoreDto();

    if (storeService.nameExist(storeDto.getStoreName())) {
      throw new CustomException(STORE_EXIST);
    }
    Store store = Store.builder()
        .partner(partner)
        .storeDescription(storeDto.getStoreDescription())
        .storeName(storeDto.getStoreName())
        .availableSeats(storeDto.getAvailableSeats())
        .storeLocation(storeDto.getStoreLocation())
        .totalSeats(storeDto.getTotalSeats())
        .build();
    storeService.saveStore(store);
    partner.getStores().add(store);
    return store;
  }
}
