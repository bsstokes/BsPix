package com.bsstokes.bspix.app.home;

import com.bsstokes.bspix.data.BsPixDatabase;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HomeControllerTest {

    @Test
    public void launches_MediaItem_when_clicked() {
        final String mediaItemId = "media-item-id";
        final HomeController.View mockView = mock(HomeController.View.class);
        final BsPixDatabase mockDatabase = mock(BsPixDatabase.class);

        final HomeController homeController = new HomeController(mockView, mockDatabase);
        homeController.onClickMediaItem(mediaItemId);

        verify(mockView).launchMediaItem(mediaItemId);
    }
}
