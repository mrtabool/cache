package com.custom.cache.service;

import java.time.LocalDateTime;
import com.custom.cache.db.entity.Street;
import com.custom.cache.db.repository.StreetRepository;
import com.custom.cache.service.cache.CacheableStreetProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StreetService {

    private final CacheableStreetProvider cacheableStreetProvider;

    public Integer getCoordinate(String streetName) {
        LocalDateTime requestDate = LocalDateTime.now();
        Street street = cacheableStreetProvider.findByName(streetName);
        if (requestDate.isAfter(street.getLastRequest())) {
            cacheableStreetProvider.setAccessDate(street, requestDate);
        }
        return street.getCoordinate();
    }
}
