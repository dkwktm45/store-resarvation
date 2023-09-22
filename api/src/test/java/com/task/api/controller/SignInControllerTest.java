package com.task.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.api.application.SignInApplication;
import com.task.api.dto.RequestUser;
import com.task.api.mailgun.MailgunClient;
import com.task.api.testObject.Helper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static com.task.common.exception.ErrorCode.REQUEST_BAD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureDataJpa
@WebMvcTest(SignInControllerTest.class)
class SignInControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SignInApplication signInApplication;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean // 빈 주입
  private RedisConnectionFactory redisConnectionFactory;
  @MockBean // 빈 주입
  private MailgunClient mailgunClient;
  @Test
  @DisplayName("로그인 요청 - 성공")
  void login_success() throws Exception{
    // given
    RequestUser.Login user = Helper.createLoginUser();
    HashMap<String, String> map = new HashMap<>();
    map.put("accessToken", "testAccessToken");
    map.put("refreshToken", "testRefreshToken");

    String content = objectMapper.writeValueAsString(user);
    given(signInApplication.userLoginToken(any()))
        .willReturn(map);

    // when && then
    mockMvc.perform(post("/login")
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(content))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.accessToken").value("testAccessToken"))
        .andExpect(jsonPath("$.refreshToken").value("testRefreshToken"))
        .andDo(print());
  }
  @Test
  @DisplayName("로그아웃 - 성공")
  void logout_success() throws Exception {
    // given
    doNothing().when(signInApplication).deleteToken(anyString());

    // when && then
    mockMvc.perform(post("/logout")
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .header("authorization", "123"))
        .andExpect(status().isOk())
        .andDo(print());
  }
  @Test
  @DisplayName("로그아웃 - 실패 : header값 없음")
  void logout_fail() throws Exception {
    // given
    doNothing().when(signInApplication).deleteToken(anyString());

    // when && then
    mockMvc.perform(post("/logout")
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("authorization을 찾지 못하고 있습니다."))
        .andExpect(jsonPath("$.httpStatus").value(REQUEST_BAD.getHttpStatus().name()))
        .andDo(print());
  }
}