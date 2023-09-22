package com.task.api.service;

import com.task.api.dto.CreatePartner;
import com.task.api.testObject.Helper;
import com.task.common.exception.CustomException;
import com.task.domain.entity.Partner;
import com.task.domain.repository.PartnerRepository;
import com.task.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.task.common.exception.ErrorCode.NOT_FOUND_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PartnerServiceTest {

  @Mock
  private PartnerRepository partnerRepository;
  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private PartnerService partnerService;
  @Test
  void createPartner_success() {
    // given
    CreatePartner.Request partner = Helper.createPartnerRequest();
    Partner savePartner = Partner.builder()
        .partnerEmail(partner.getEmail())
        .partnerName(partner.getPartnerName())
        .build();

    when(userRepository.existsByEmail(partner.getEmail()))
        .thenReturn(true);
    when(partnerRepository.save(savePartner));
    // when

    String result = partnerService.createPartner(partner);
    // then
    assertEquals(result,"파트너 가입이 되었습니다.");
  }
  @Test
  void createPartner_fail_user() {
    // given
    CreatePartner.Request partner = Helper.createPartnerRequest();

    when(userRepository.existsByEmail(partner.getEmail()))
        .thenReturn(false);
    // when

    CustomException exception = assertThrows(CustomException.class,
        () -> partnerService.createPartner(partner));
    // then
    assertEquals(exception.getErrorCode(),NOT_FOUND_USER);
  }

}