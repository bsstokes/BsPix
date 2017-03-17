package com.bsstokes.bspix.auth;

import com.bsstokes.bspix.api.InstagramApi;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LoginControllerTest {

    private LoginController.View mockView;
    private LoginController controller;

    @Before
    public void setUp() {
        mockView = mock(LoginController.View.class);
        controller = new LoginController(mockView);
    }

    @Test
    public void startingControllerLoadsInstagramAuthPage() {
        controller.start();
        verify(mockView).loadUrl(InstagramApi.AUTHORIZE_URL);
    }
}
