package com.custom.cache.service;

import java.time.LocalDateTime;
import java.util.Random;
import com.custom.cache.db.entity.Street;
import org.springframework.stereotype.Component;

@Component
public class StreetBuilder {
    public Street buildStreet(String name) {
        Street street = new Street();
        street.setName(name);
        int coordinate = new Random().nextInt(1000);
        street.setCoordinate(coordinate);
        street.setLastRequest(LocalDateTime.now());
        return street;
    }
}
