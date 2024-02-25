package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;

class MessageSenderImplTest {
    public GeoService geoServiceMock;
    public LocalizationService localizationServiceMock;
    public MessageSenderImpl messageSenderMock;

    @BeforeEach
    public void init() {
        geoServiceMock = Mockito.mock(GeoServiceImpl.class);
        localizationServiceMock = Mockito.mock(LocalizationServiceImpl.class);
        messageSenderMock = new MessageSenderImpl(geoServiceMock, localizationServiceMock);
    }

    @Test
    void sendInRus() {
        Mockito.when(geoServiceMock.byIp("172.")).thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));
        Mockito.when(localizationServiceMock.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        Map<String, String> headers = new HashMap<>();
        headers.put(messageSenderMock.IP_ADDRESS_HEADER, "172.");
        Assertions.assertEquals("Добро пожаловать", messageSenderMock.send(headers));
    }


    @Test
    void sendInEng() {
        Mockito.when(geoServiceMock.byIp("96.")).thenReturn(new Location("New York", Country.USA, " 10th Avenue", 32));
        Mockito.when(localizationServiceMock.locale(Country.USA)).thenReturn("Welcome");
        Map<String, String> headers = new HashMap<>();
        headers.put(messageSenderMock.IP_ADDRESS_HEADER, "96.");
        Assertions.assertEquals("Welcome", messageSenderMock.send(headers));
    }
}