package com.task.api.service;

import com.task.api.dto.CreatePartner;
import com.task.common.exception.CustomException;
import com.task.domain.entity.Partner;
import com.task.domain.repository.PartnerRepository;
import com.task.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.task.common.exception.ErrorCode.NOT_FOUND_USER;

@RequiredArgsConstructor
@Service
public class PartnerService {
  private final PartnerRepository partnerRepository;
  private final UserRepository userRepository;
  public String createPartner(CreatePartner.Request partnerRequest) {
    if (!userRepository.findByEmailExists(partnerRequest.getEmail())) {
      throw new CustomException(NOT_FOUND_USER);
    }

    partnerRepository.save(
        Partner.builder()
            .partnerEmail(partnerRequest.getEmail())
            .partnerName(partnerRequest.getPartnerName())
            .build()
    );
    return "파트너 가입이 되었습니다.";
  }
}
