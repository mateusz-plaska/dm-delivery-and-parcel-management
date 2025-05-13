package org.delivery.inMemory;

import java.util.List;
import org.delivery.dto.DeliveryData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("deliveryRepository")
public class DeliveryRepository extends InMemoryRepositoryImpl<DeliveryData> {

    public DeliveryRepository(@Value("${serialization.location.delivery}") String filePath) {
        super(filePath);
    }

    @Override
    protected void loadInitData() {
        List<DeliveryData> deliveries = List.of(
                new DeliveryData("101", "445566", "Oczekuje na kuriera", "Poznań, Polska", "2025-05-20T09:00:00Z"),
                new DeliveryData("104", "223344", "Oczekuje na kuriera", "Szczecin, Polska", "2025-05-22T11:45:00Z"),
                new DeliveryData("213", "123456", "W transporcie", "Warszawa, Polska", "2025-05-15T10:00:00Z"),
                new DeliveryData("202", "778899", "W transporcie", "Wrocław, Polska", "2025-05-16T12:30:00Z"),
                new DeliveryData("307", "334455", "Dostarczone", "Białystok, Polska", "2025-05-12T10:20:00Z"),
                new DeliveryData("405", "556677", "W przygotowaniu", "Lublin, Polska", "2025-05-17T14:00:00Z"),
                new DeliveryData("489", "112233", "W przygotowaniu", "Gdańsk, Polska", "2025-05-18T16:45:00Z"),
                new DeliveryData("456", "654321", "W przygotowaniu", "Kraków, Polska", "2025-05-10T14:30:00Z"),
                new DeliveryData("406", "889900", "W przygotowaniu", "Katowice, Polska", "2025-05-19T08:30:00Z"),
                new DeliveryData("403", "990011", "W przygotowaniu", "Łódź, Polska", "2025-05-08T15:15:00Z"));
        deliveries.forEach(d -> data.put(compositeKey(d.getUserId(), d.getTrackingNumber()), d));
    }
}
