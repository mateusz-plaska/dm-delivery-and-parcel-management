package org.delivery.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeliveryData implements Serializable {
    private final String userId;
    private final String trackingNumber;
    private String status;
    private String location;
    private String estimatedDelivery;
}
