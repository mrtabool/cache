package com.custom.cache;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.custom.cache.db.entity.Street;
import com.custom.cache.db.repository.StreetRepository;
import com.custom.cache.service.StreetBuilder;
import com.custom.cache.service.cache.CacheableStreetProvider;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class CacheApplicationTests {

    private final static String STREET_NAME = "Wall Street";

    @Autowired
    private CacheableStreetProvider cacheableStreetProvider;
    @Autowired
    private CacheManager cacheManager;

    @MockBean
    private StreetBuilder streetBuilderMock;
    @MockBean
    private StreetRepository streetRepositoryMock;

    @AfterEach
    public void afterEach() {
        Cache streets = cacheManager.getCache("streets");
        streets.clear();
    }

    @Test
    void findByName_ifStreetExists() {
        StreetBuilder streetBuilder = new StreetBuilder();
        Street streetMock = streetBuilder.buildStreet(STREET_NAME);

        when(streetRepositoryMock.findByName(STREET_NAME)).thenReturn(streetMock);
        when(streetBuilderMock.buildStreet(STREET_NAME)).thenReturn(null);

        Street street = cacheableStreetProvider.findByName(STREET_NAME);

        assertThat(street.getCoordinate()).isEqualTo(streetMock.getCoordinate());
    }

    @Test
    void findByName_ifStreetNotExists() {
        StreetBuilder streetBuilder = new StreetBuilder();
        Street streetMock = streetBuilder.buildStreet(STREET_NAME);

        when(streetBuilderMock.buildStreet(STREET_NAME)).thenReturn(streetMock);
        when(streetRepositoryMock.findByName(STREET_NAME)).thenReturn(null);
        when(streetRepositoryMock.save(streetMock)).thenReturn(streetMock);

        Street street = cacheableStreetProvider.findByName(STREET_NAME);

        assertThat(streetMock.getCoordinate()).isEqualTo(street.getCoordinate());
    }
}
