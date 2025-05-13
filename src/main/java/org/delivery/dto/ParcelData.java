package org.delivery.dto;

import java.io.Serializable;

public record ParcelData(String userId, String trackingNumber, String lockerId, String packageSize)
        implements Serializable {}
