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

import java.util.List;

import static com.task.common.exception.ErrorCode.STORE_EXIST;
import static com.task.common.exception.ErrorCode.STORE_REG_ONE;

@Service
@RequiredArgsConstructor
public class PartnerApplication {
  private final PartnerService partnerService;
  private final com.task.api.service.StoreService storeService;
  @Transactional
  public Store registStore(CreatePartner.Store req) {
    Partner partner = partnerService.getPartner(req.getEmail());

    List<StoreDto> storeDtos = req.getStoreDtoList();
    if (storeDtos.size() > 2) {
      throw new CustomException(STORE_REG_ONE);
    }
    StoreDto storeDto = storeDtos.get(0);

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
