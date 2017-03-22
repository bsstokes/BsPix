package com.bsstokes.bspix.app.home.follows;

import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.data.User;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FollowsControllerTest {

    private FollowsController followsController;
    private FollowsController.View mockView;
    private BsPixDatabase mockBsPixDatabase;

    @Before
    public void setUp() {
        mockView = mock(FollowsController.View.class);
        mockBsPixDatabase = mock(BsPixDatabase.class);
        followsController = new FollowsController(mockView, mockBsPixDatabase);
    }

    @Test
    public void sets_follows_on_view() {
        final List<User> mockFollows = mock(List.class);
        when(mockBsPixDatabase.getFollows()).thenReturn(Observable.just(mockFollows));
        // TODO: Figure out how to mock or override AndroidSchedulers.mainThread()

        followsController.load(Schedulers.immediate());
        verify(mockView).setFollows(mockFollows);
    }
}
