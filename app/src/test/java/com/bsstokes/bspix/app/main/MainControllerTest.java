package com.bsstokes.bspix.app.main;

import com.bsstokes.bspix.auth.Account;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainControllerTest {

    private Account mockAccount;
    private MainContract.View mockView;

    private MainController mainController;

    @Before
    public void setUp() {
        mockAccount = Mockito.mock(Account.class);
        mockView = Mockito.mock(MainContract.View.class);
        mainController = new MainController(mockView, mockAccount);
    }

    @Test
    public void whenLoggedInShouldStartHomeActivity() {
        when(mockAccount.isLoggedIn()).thenReturn(true);
        mainController.start();
        verify(mockView).launchHomeActivity();
    }

    @Test
    public void whenNotLoggedInShouldStartLoginActivity() {
        when(mockAccount.isLoggedIn()).thenReturn(false);
        mainController.start();
        verify(mockView).launchLoginActivity();
    }
}