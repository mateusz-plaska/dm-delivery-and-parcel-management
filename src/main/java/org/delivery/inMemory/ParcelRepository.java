package org.delivery.inMemory;

import org.delivery.dto.ParcelData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("parcelRepository")
public class ParcelRepository extends InMemoryRepositoryImpl<ParcelData> {

    public ParcelRepository(@Value("${serialization.location.parcel}") String filePath) {
        super(filePath);
    }

    @Override
    protected void loadInitData() {
        List<ParcelData> parcels = List.of(
                new ParcelData("101", "445566", "lockerA", "mały"),
                new ParcelData("104", "223344", "lockerB", "średni"),
                new ParcelData("213", "123456", "lockerC", "duży"),
                new ParcelData("202", "778899", "lockerD", "mały"),
                new ParcelData("307", "334455", "lockerB", "średni")
        );
        parcels.forEach(p -> data.put(compositeKey(p.userId(), p.trackingNumber()), p));
    }
}
