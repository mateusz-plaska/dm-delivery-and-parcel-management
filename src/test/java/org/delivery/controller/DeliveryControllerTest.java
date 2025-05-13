package org.delivery.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.delivery.dto.DeliveryData;
import org.delivery.exception.ResourceNotFoundException;
import org.delivery.service.DeliveryServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = DeliveryController.class)
class DeliveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeliveryServiceImpl deliveryService;

    @Test
    void whenValidRequest_thenReturnsDeliveryData() throws Exception {
        // given
        DeliveryData sample = new DeliveryData("user1", "track1", "Dostarczone", "Warszawa", "2025-05-10T10:00:00Z");
        when(deliveryService.getDeliveryInfo("user1", "track1")).thenReturn(sample);

        // when
        mockMvc.perform(get("/track/user1/track1").accept(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Dostarczone"))
                .andExpect(jsonPath("$.location").value("Warszawa"));
    }

    @Test
    void whenNotFound_invalidID_thenReturnsNotFound() throws Exception {
        // given
        when(deliveryService.getDeliveryInfo("u", "t")).thenThrow(new ResourceNotFoundException("not found"));

        // when
        mockMvc.perform(get("/track/u/t").accept(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("not found")));
    }
}
