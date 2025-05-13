package org.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.delivery.dto.ParcelData;
import org.delivery.dto.ParcelResponse;
import org.delivery.service.ParcelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/packages")
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelService parcelService;

    @PostMapping(value = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParcelResponse> createPackage(@RequestBody ParcelData parcelData) {
        return new ResponseEntity<>(parcelService.addParcel(parcelData), HttpStatus.CREATED);
    }
}
