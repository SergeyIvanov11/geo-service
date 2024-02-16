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
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

class MainTest {
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
    void messageInRus() {
        Mockito.when(geoServiceMock.byIp("172.")).thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));
        Mockito.when(localizationServiceMock.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        Map<String, String> headers = new HashMap<>();
        headers.put(messageSenderMock.IP_ADDRESS_HEADER, "172.");
        Assertions.assertEquals("Добро пожаловать", messageSenderMock.send(headers));
    }

    @Test
    void messageInEng() {
        Mockito.when(geoServiceMock.byIp("96.")).thenReturn(new Location("New York", Country.USA, " 10th Avenue", 32));
        Mockito.when(localizationServiceMock.locale(Country.USA)).thenReturn("Welcome");
        Map<String, String> headers = new HashMap<>();
        headers.put(messageSenderMock.IP_ADDRESS_HEADER, "96.");
        Assertions.assertEquals("Welcome", messageSenderMock.send(headers));
    }

    @Test
    void byIpTest() {
        GeoService geoServiceSpy = Mockito.spy(GeoServiceImpl.class);
        Location actualNewYork = geoServiceSpy.byIp("96.");
        Location actualMoscow = geoServiceSpy.byIp("172.0.32.11");
        Assertions.assertEquals(Country.USA, actualNewYork.getCountry());
        Assertions.assertEquals(Country.RUSSIA, actualMoscow.getCountry());
    }

    @Test
    void localeTest() {
        LocalizationService localizationServiceSpy = Mockito.spy(LocalizationServiceImpl.class);
        Assertions.assertEquals("Welcome", localizationServiceSpy.locale(Country.USA));
        Assertions.assertEquals("Добро пожаловать", localizationServiceSpy.locale(Country.RUSSIA));
    }
}