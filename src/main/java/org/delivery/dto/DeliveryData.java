package org.delivery.dto;

import java.io.Serializable;

public record DeliveryData(String userId, String trackingNumber, String status, String location,
                           String estimatedDelivery) implements Serializable {}
