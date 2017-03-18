package com.bsstokes.bspix.auth;

import com.bsstokes.bspix.api.InstagramApi;

import org.junit.Before;
import org.junit.Test;

import okhttp3.HttpUrl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class LoginControllerTest {

    private Account mockAccount;
    private LoginController.View mockView;
    private LoginController loginController;

    @Before
    public void setUp() {
        mockAccount = mock(Account.class);
        mockView = mock(LoginController.View.class);
        loginController = new LoginController(mockView, mockAccount);
    }

    @Test
    public void startingControllerLoadsInstagramAuthPage() {
        loginController.start();
        verify(mockView).loadUrl(InstagramApi.AUTHORIZE_URL);
    }

    @Test
    public void loadingAPageThatIsNotRedirectPageKeepsGoing() {
        final String url = "This is not a valid redirect page";
        loginController.onLoadPage(url);
        verifyNoMoreInteractions(mockView);
    }

    @Test
    public void loadingAPageThatIsRedirectPageLogsInAndClosesActivity() {
        final HttpUrl redirectUrl = InstagramApi.REDIRECT_URL.newBuilder()
                .fragment("access_token=MY-ACCESS-TOKEN")
                .build();
        loginController.onLoadPage(redirectUrl.toString());

        verify(mockAccount).logIn("MY-ACCESS-TOKEN");
        verify(mockView).finish();
    }
}
