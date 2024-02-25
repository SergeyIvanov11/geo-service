package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

class GeoServiceImplTest {

    @Test
    void byIp() {
        GeoService geoServiceSpy = Mockito.spy(GeoServiceImpl.class);
        Location actualNewYork = geoServiceSpy.byIp("96.");
        Location actualMoscow = geoServiceSpy.byIp("172.0.32.11");
        Assertions.assertEquals(Country.USA, actualNewYork.getCountry());
        Assertions.assertEquals(Country.RUSSIA, actualMoscow.getCountry());
    }
}