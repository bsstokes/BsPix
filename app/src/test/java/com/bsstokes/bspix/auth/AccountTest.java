package com.bsstokes.bspix.auth;

import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.settings.BsPixSettings;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountTest {

    private Account account;
    private BsPixSettings mockSettings;
    private BsPixDatabase mockDatabase;

    @Before
    public void setUp() {
        mockSettings = mock(BsPixSettings.class);
        mockDatabase = mock(BsPixDatabase.class);
        account = new Account(mockSettings, mockDatabase);
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

    @Test
    public void loggingOutClearsCookiesInSettings() {
        account.logOut();
        verify(mockSettings).clearCookies();
    }

    @Test
    public void getsAccessTokenFromSettings() {
        final String accessToken = "my.access.token";
        when(mockSettings.getAccessToken()).thenReturn(accessToken);
        assertEquals(accessToken, account.getAccessToken());
    }

    @Test
    public void logging_out_clears_users() {
        account.logOut();
        verify(mockDatabase).deleteAllUsers();
    }

    @Test
    public void logging_out_clears_media() {
        account.logOut();
        verify(mockDatabase).deleteAllMedia();
    }

    @Test
    public void logging_out_clears_liked_media() {
        account.logOut();
        verify(mockDatabase).deleteAllLikedMedia();
    }
}
