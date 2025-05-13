package org.delivery.service;

import lombok.RequiredArgsConstructor;
import org.delivery.dto.DeliveryData;
import org.delivery.exception.ResourceNotFoundException;
import org.delivery.inMemory.DeliveryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    @Override
    public DeliveryData getDeliveryInfo(String userId, String trackingNumber) {
        DeliveryData deliveryData = deliveryRepository.get(userId, trackingNumber);
        if (deliveryData == null) {
            throw new ResourceNotFoundException("Delivery not found for userId=" + userId
                    + ", and trackingNumber=" + trackingNumber);
        }
        return deliveryData;
    }
}
