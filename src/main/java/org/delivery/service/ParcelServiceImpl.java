package org.delivery.service;

import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.delivery.dto.DeliveryData;
import org.delivery.dto.ParcelData;
import org.delivery.dto.ParcelResponse;
import org.delivery.exception.IllegalIdException;
import org.delivery.inMemory.DeliveryRepository;
import org.delivery.inMemory.ParcelRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParcelServiceImpl implements ParcelService {

    private final ParcelRepository parcelRepository;
    private final DeliveryRepository deliveryRepository;

    @Override
    public ParcelResponse addParcel(ParcelData parcelData) {
        if (parcelRepository.get(parcelData.userId(), parcelData.trackingNumber()) != null) {
            throw new IllegalIdException("Parcel with userId=" + parcelData.userId() + ", and trackingNumber="
                    + parcelData.trackingNumber() + " already exists");
        }

        DeliveryData delivery = deliveryRepository.get(parcelData.userId(), parcelData.trackingNumber());
        if (delivery == null) {
            throw new IllegalIdException("Delivery with userId=" + parcelData.userId() + ", and trackingNumber="
                    + parcelData.trackingNumber() + " does not exist");
        }

        Set<Map.Entry<String, ParcelData>> parcelEntriesBackup = parcelRepository.entries();
        Set<Map.Entry<String, DeliveryData>> deliveryEntriesBackup = deliveryRepository.entries();

        try {
            parcelRepository.add(parcelData.userId(), parcelData.trackingNumber(), parcelData);
            delivery.setStatus("Oczekuje na kuriera");
        } catch (Exception exception) {
            parcelRepository.clearAll();
            deliveryRepository.clearAll();
            parcelRepository.addAll(parcelEntriesBackup);
            deliveryRepository.addAll(deliveryEntriesBackup);
            throw exception;
        }

        return new ParcelResponse("Paczka wysłana pomyślnie", parcelData.trackingNumber());
    }
}
