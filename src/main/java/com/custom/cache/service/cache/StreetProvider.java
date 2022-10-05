package com.custom.cache.service.cache;

import java.time.LocalDateTime;
import com.custom.cache.db.entity.Street;

public interface StreetProvider {
    public Street findByName(String name);

    public Street setAccessDate(Street street, LocalDateTime time);
}
