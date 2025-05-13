package org.delivery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.delivery.dto.DeliveryData;
import org.delivery.exception.ResourceNotFoundException;
import org.delivery.inMemory.DeliveryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceImplTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DeliveryServiceImpl deliveryService;

    @Test
    void whenExists_thenReturnData() {
        // given
        DeliveryData d = new DeliveryData("u", "t", "S", "L", "D");
        when(deliveryRepository.get("u", "t")).thenReturn(d);

        // when
        DeliveryData result = deliveryService.getDeliveryInfo("u", "t");

        // then
        assertEquals(result, d);
    }

    @Test
    void whenMissing_thenThrow() {
        // given
        when(deliveryRepository.get("u", "x")).thenReturn(null);

        // when
        // then
        assertThrows(ResourceNotFoundException.class, () -> deliveryService.getDeliveryInfo("u", "x"));
    }
}
