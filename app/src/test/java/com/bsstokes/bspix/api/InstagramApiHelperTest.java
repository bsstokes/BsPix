package com.bsstokes.bspix.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class InstagramApiHelperTest {

    @Test
    public void invalidRedirectUrlIsNotValid() {
        assertFalse(InstagramApiHelper.isValidRedirectUrl(null));
        assertFalse(InstagramApiHelper.isValidRedirectUrl(""));
        assertFalse(InstagramApiHelper.isValidRedirectUrl("http://google.com/"));
        assertFalse(InstagramApiHelper.isValidRedirectUrl("Not a URL"));
    }

    @Test
    public void validRedirectUrlIsValid() {
        assertTrue(InstagramApiHelper.isValidRedirectUrl(InstagramApi.REDIRECT_URL.toString()));
    }

    @Test
    public void doesNotGetAccessTokenWhenItIsMissing() {
        final String url = InstagramApi.REDIRECT_URL.toString();
        assertNull(InstagramApiHelper.getAccessToken(url));
    }

    @Test
    public void doesNotGetAccessTokenWhenItIsMissingValue() {
        final String url = InstagramApi.REDIRECT_URL.toString() + "#access_token=";
        assertNull(InstagramApiHelper.getAccessToken(url));
    }

    @Test
    public void getsAccessTokenWhenItsThere() {
        final String accessToken = "ThisIsMyAccessToken";
        final String url = InstagramApi.REDIRECT_URL.toString() + "#access_token=" + accessToken;
        assertEquals(accessToken, InstagramApiHelper.getAccessToken(url));
    }
}
