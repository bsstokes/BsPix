package com.bsstokes.bspix.app.home.my_media;

import com.bsstokes.bspix.data.BsPixDatabase;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MyMediaControllerTest {

    @Test
    public void launches_MediaItem_when_clicked() {
        final String mediaItemId = "media-item-id";
        final MyMediaController.View mockView = mock(MyMediaController.View.class);
        final BsPixDatabase mockDatabase = mock(BsPixDatabase.class);

        final MyMediaController myMediaController = new MyMediaController(mockView, mockDatabase);
        myMediaController.onClickMediaItem(mediaItemId);

        verify(mockView).launchMediaItem(mediaItemId);
    }
}
