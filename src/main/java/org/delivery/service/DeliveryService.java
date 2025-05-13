package org.delivery.service;

import org.delivery.dto.DeliveryData;

public interface DeliveryService {
    DeliveryData getDeliveryInfo(String userId, String trackingNumber);
}
