package org.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.dto.DeliveryData;
import org.delivery.service.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
class DeliveryController {
    private final DeliveryService deliveryService;

    @GetMapping(path = "/track/{userId}/{trackingNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeliveryData> getDelivery(@PathVariable String userId, @PathVariable String trackingNumber) {
        return new ResponseEntity<>(deliveryService.getDeliveryInfo(userId, trackingNumber), HttpStatus.OK);
    }
}
