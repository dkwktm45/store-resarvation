package com.task.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.api.application.SignUpApplication;
import com.task.api.dto.CreateUser;
import com.task.api.testObject.Helper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.task.api.dto.message.ResponseType.JOIN_ADMIN;
import static com.task.api.dto.message.ResponseType.JOIN_USER;
import static com.task.common.exception.ErrorCode.REQUEST_BAD;
import static com.task.domain.type.UserType.ADMIN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureDataJpa
@WebMvcTest(SignUpController.class)
public class SignUpControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private SignUpApplication signUpApplication;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("일반 유저 회원가입 요청")
  void joinUserRequest() throws Exception {
    // given
    String content = objectMapper.writeValueAsString(Helper.createUserRequest());
    given(signUpApplication.signUpUser(any()))
        .willReturn(JOIN_USER.getMessage());

    // when && then
    mockMvc.perform(post("/join")
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(content))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(JOIN_USER.getMessage()))
        .andDo(print());
  }

  @Test
  @DisplayName("요청에 따른 ValidException [NotBlank]")
  void badNotBlankRequest() throws Exception {
    // given
    CreateUser.Request req = CreateUser.Request.builder()
        .build();
    String content = objectMapper.writeValueAsString(req);

    // when && then
    mockMvc.perform(post("/join")
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(content))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("비밀번호는 필수로 입력 값입니다."))
        .andExpect(jsonPath("$.httpStatus").value(REQUEST_BAD.getHttpStatus().name()))
        .andDo(print());
  }
  @Test
  @DisplayName("요청에 따른 ValidException [Email]")
  void notValidEmailRequest() throws Exception {
    // given
    CreateUser.Request req = CreateUser.Request.builder()
        .email("mweklmwkae")
        .password("123")
        .userName("1")
        .userType(ADMIN).build();
    String content = objectMapper.writeValueAsString(req);

    // when && then
    mockMvc.perform(post("/join")
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(content))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("이메일 형식이 올바르지 않습니다."))
        .andExpect(jsonPath("$.httpStatus").value(REQUEST_BAD.getHttpStatus().name()))
        .andDo(print());
  }

  @Test
  @DisplayName("요청에 따른 ValidException [Email]")
  void notValidUserTypeRequest() throws Exception {
    // given
    CreateUser.Request req = CreateUser.Request.builder()
        .email("wpekdl153@gmail.com")
        .password("123")
        .userName("1")
        .userType(null).build();
    String content = objectMapper.writeValueAsString(req);

    // when && then
    mockMvc.perform(post("/join")
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(content))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("UserType이 아닙니다."))
        .andExpect(jsonPath("$.httpStatus").value(REQUEST_BAD.getHttpStatus().name()))
        .andDo(print());
  }
  @Test
  @DisplayName("점주 유저 회원가입 요청")
  void joinAdminRequest() throws Exception {
    // given
    String content = objectMapper.writeValueAsString(Helper.createUserRequest());
    given(signUpApplication.signUpUser(any()))
        .willReturn(JOIN_ADMIN.getMessage());

    // when && then
    mockMvc.perform(post("/join")
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(content))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(JOIN_ADMIN.getMessage()))
        .andDo(print());
  }
}