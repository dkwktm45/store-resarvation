package com.task.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.api.dto.CreatePartner;
import com.task.api.mailgun.MailgunClient;
import com.task.api.service.PartnerService;
import com.task.api.testObject.Helper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureDataJpa
@WebMvcTest(value = PartnerControllerTest.class)
@AutoConfigureMockMvc(addFilters = false)
class PartnerControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PartnerService partnerService;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean // 빈 주입
  private RedisConnectionFactory redisConnectionFactory;
  @MockBean // 빈 주입
  private MailgunClient mailgunClient;

  @Test
  @DisplayName("파트너 등록 - 성공")
  void regPartner() throws Exception {
    CreatePartner.Request req = Helper.createPartnerRequest();
    String content = objectMapper.writeValueAsString(req);
    String answer = "파트너 가입이 되었습니다.";
    given(partnerService.createPartner(any()))
        .willReturn(answer);

    // when && then
    mockMvc.perform(post("/reservation/partner/registration")
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(content))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(answer))
        .andDo(print());
  }
}