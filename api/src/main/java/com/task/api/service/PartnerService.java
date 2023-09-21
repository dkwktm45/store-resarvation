package com.task.api.service;

import com.task.api.dto.CreatePartner;
import com.task.domain.entity.Partner;
import com.task.domain.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PartnerService {
  private final PartnerRepository partnerRepository;
  public String createPartner(CreatePartner.Request partnerRequest) {
    partnerRepository.save(
        Partner.builder()
            .partnerEmail(partnerRequest.getEmail())
            .partnerName(partnerRequest.getPartnerName())
            .build()
    );
    return "파트너 가입이 되었습니다.";
  }
}
