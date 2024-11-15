package com.xische.exchangerate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test class for MessageService.
 */
class MessageServiceTest {

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getMessage_withValidInputs_shouldReturnExpectedMessage() {
        // Arrange
        String code = "greeting.message";
        Object[] args = {"John"};
        Locale locale = Locale.ENGLISH;
        String expectedMessage = "Hello, John!";

        when(messageSource.getMessage(code, args, locale)).thenReturn(expectedMessage);

        // Act
        String actualMessage = messageService.getMessage(code, args, locale);

        // Assert
        assertEquals(expectedMessage, actualMessage);
        verify(messageSource, times(1)).getMessage(code, args, locale);
    }

    @Test
    void getMessage_withNullArgs_shouldReturnMessageWithoutArguments() {
        // Arrange
        String code = "welcome.message";
        Object[] args = null;
        Locale locale = Locale.FRENCH;
        String expectedMessage = "Bienvenue!";

        when(messageSource.getMessage(code, args, locale)).thenReturn(expectedMessage);

        // Act
        String actualMessage = messageService.getMessage(code, args, locale);

        // Assert
        assertEquals(expectedMessage, actualMessage);
        verify(messageSource, times(1)).getMessage(code, args, locale);
    }

    @Test
    void getMessage_withNullLocale_shouldUseDefaultLocale() {
        // Arrange
        String code = "farewell.message";
        Object[] args = {"Alice"};
        Locale locale = null;
        String expectedMessage = "Goodbye, Alice!";

        when(messageSource.getMessage(code, args, Locale.getDefault())).thenReturn(expectedMessage);

        // Act
        String actualMessage = messageService.getMessage(code, args, Locale.getDefault());

        // Assert
        assertEquals(expectedMessage, actualMessage);
        verify(messageSource, times(1)).getMessage(code, args, Locale.getDefault());
    }
}