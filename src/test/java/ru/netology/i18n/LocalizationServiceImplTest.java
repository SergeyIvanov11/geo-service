package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;

class LocalizationServiceImplTest {

    @Test
    void locale() {
        LocalizationService localizationServiceSpy = Mockito.spy(LocalizationServiceImpl.class);
        Assertions.assertEquals("Welcome", localizationServiceSpy.locale(Country.USA));
        Assertions.assertEquals("Добро пожаловать", localizationServiceSpy.locale(Country.RUSSIA));
    }
}