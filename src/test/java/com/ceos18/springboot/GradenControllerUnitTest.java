package com.ceos18.springboot;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {GardenController.class})
public class GradenControllerUnitTest {
  
  @Autowired
  private MockMvc mockMvc;
  
  @Test
  @DisplayName("기본 컨트롤러를 유닛 테스트한다.")
  public void 컨트롤러에서_STATUS_200_반환하기() throws Exception {
    // when & then
    mockMvc.perform(get("/")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
