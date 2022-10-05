package com.custom.cache.service.cache;

import java.time.LocalDateTime;
import com.custom.cache.db.entity.Street;
import com.custom.cache.db.repository.StreetRepository;
import com.custom.cache.service.StreetBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CacheableStreetProvider implements StreetProvider {

    private final StreetRepository streetRepository;
    private final StreetBuilder streetBuilder;

    @Cacheable(cacheNames = "streets", key = "#p0")
    @Override
    public Street findByName(String name) {
        Street streetStored = streetRepository.findByName(name);
        if (streetStored != null) {
            return streetStored;
        } else {
            Street street = streetBuilder.buildStreet(name);
            return streetRepository.save(street);
        }
    }

    @CachePut(cacheNames = "streets", key = "#street.name")
    @Override
    public Street setAccessDate(Street street, LocalDateTime time) {
        streetRepository.setLastRequest(street.getId(), time);
        return street;
    }
}
