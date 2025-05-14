package org.delivery.service;

import org.delivery.dto.DeliveryData;

import java.util.List;

public interface DeliveryService {
    DeliveryData getDeliveryInfo(String userId, String trackingNumber);

    List<DeliveryData> getAllDeliveriesFromUser(String userId);
}
