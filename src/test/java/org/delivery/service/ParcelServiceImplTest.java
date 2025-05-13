package org.delivery.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.delivery.dto.DeliveryData;
import org.delivery.dto.ParcelData;
import org.delivery.dto.ParcelResponse;
import org.delivery.exception.IllegalIdException;
import org.delivery.inMemory.DeliveryRepository;
import org.delivery.inMemory.ParcelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParcelServiceImplTest {

    @Mock
    private ParcelRepository parcelRepository;

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private ParcelServiceImpl parcelService;

    @Test
    void whenNewParcelCorrect_thenReturnResponse() {
        // given
        ParcelData p = new ParcelData("u", "t", "l", "s");
        DeliveryData d = new DeliveryData("u", "t", "S", "L", "D");
        when(parcelRepository.get("u", "t")).thenReturn(null);
        when(deliveryRepository.get("u", "t")).thenReturn(d);

        // when
        ParcelResponse parcelResponse = parcelService.addParcel(p);
        assertEquals("Paczka wysłana pomyślnie", parcelResponse.message());
        assertEquals(parcelResponse.trackingNumber(), p.trackingNumber());
    }

    @Test
    void whenParcelExists_thenThrow() {
        // given
        ParcelData p = new ParcelData("u", "t", "l", "s");
        when(parcelRepository.get("u", "t")).thenReturn(p);

        // when
        // then
        assertThrows(IllegalIdException.class, () -> parcelService.addParcel(p));
    }

    @Test
    void whenMissingBoth_thenThrow() {
        // given
        ParcelData p = new ParcelData("u", "t", "l", "s");
        when(parcelRepository.get("u", "t")).thenReturn(null);
        when(deliveryRepository.get("u", "t")).thenReturn(null);

        // when
        // then
        assertThrows(IllegalIdException.class, () -> parcelService.addParcel(p));
    }
}
