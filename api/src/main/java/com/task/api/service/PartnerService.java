package com.task.api.service;

import com.task.api.dto.CreatePartner;
import com.task.common.exception.CustomException;
import com.task.domain.entity.Partner;
import com.task.domain.repository.PartnerRepository;
import com.task.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.task.common.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class PartnerService {
  private final PartnerRepository partnerRepository;
  private final UserRepository userRepository;

  /**
   * 파트너 등록 : 이메일에 관한 검증을 통해서 없을시 회원가입이 이루어진다.
   * */
  @Transactional
  public String createPartner(CreatePartner.Request partnerRequest) {
    if (!partnerRepository.existsByPartnerEmail(partnerRequest.getEmail())) {
      throw new CustomException(PARTNER_EXIST);
    }

    partnerRepository.save(
        Partner.builder()
            .partnerEmail(partnerRequest.getEmail())
            .partnerName(partnerRequest.getPartnerName())
            .build()
    );
    return "파트너 가입이 되었습니다.";
  }

  /**
   * 파트너 Object를 반환하는 메소드
   * - 매개변수는 Email를 통해서 구현이 된다.
   * */
  @Transactional(readOnly = true)
  public Partner getPartner(String email) {
    return partnerRepository.findByPartnerEmail(email)
        .orElseThrow(() -> new CustomException(PARTNER_NOT_EXIST));
  }
}
