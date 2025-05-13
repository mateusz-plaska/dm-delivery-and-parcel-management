package org.delivery.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.delivery.dto.ParcelData;
import org.delivery.dto.ParcelResponse;
import org.delivery.exception.IllegalIdException;
import org.delivery.service.ParcelServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ParcelController.class)
class ParcelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ParcelServiceImpl parcelService;

    @Test
    void whenValidParcel_thenReturnsCreated() throws Exception {
        // given
        ParcelData parcelRequest = new ParcelData("user", "track", "lock8", "mała");
        ParcelResponse parcelResponse = new ParcelResponse("Paczka wysłana pomyślnie", "track");
        when(parcelService.addParcel(parcelRequest)).thenReturn(parcelResponse);

        // when
        mockMvc.perform(post("/packages/send")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parcelRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Paczka wysłana pomyślnie"))
                .andExpect(jsonPath("$.trackingNumber").value("track"));
    }

    @Test
    void whenDuplicateParcel_thenReturnsBadRequest() throws Exception {
        // given
        ParcelData request = new ParcelData("user1", "track1", "lockerA", "mały");
        when(parcelService.addParcel(request)).thenThrow(new IllegalIdException("already exists"));

        // when
        mockMvc.perform(post("/packages/send")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                // then
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid given id")))
                .andExpect(content().string(containsString("already exists")));
    }
}
