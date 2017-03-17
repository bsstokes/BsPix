package com.bsstokes.bspix.auth;

import com.bsstokes.bspix.settings.BsPixSettings;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountTest {

    private Account account;
    private BsPixSettings mockSettings;

    @Before
    public void setUp() {
        mockSettings = mock(BsPixSettings.class);
        account = new Account(mockSettings);
    }

    @Test
    public void isLoggedInWhenSettingsHasAccessToken() {
        when(mockSettings.getAccessToken()).thenReturn(null);
        assertFalse(account.isLoggedIn());
    }

    @Test
    public void isNotLoggedInWhenSettingsDoesNotHaveAccessToken() {
        when(mockSettings.getAccessToken()).thenReturn("access-token");
        assertTrue(account.isLoggedIn());
    }

    @Test
    public void loggingInSavesAccessTokenInSettings() {
        final String accessToken = "FAKE-ACCESS-TOKEN";
        account.logIn(accessToken);
        verify(mockSettings).setAccessToken(accessToken);
    }

    @Test
    public void loggingOutClearsAccessTokenInSettings() {
        account.logOut();
        verify(mockSettings).setAccessToken(null);
    }
}
