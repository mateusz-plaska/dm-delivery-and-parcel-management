package org.delivery.service;

import org.delivery.dto.ParcelData;
import org.delivery.dto.ParcelResponse;

public interface ParcelService {
    ParcelResponse addParcel(ParcelData parcelData);
}
