package com.bsstokes.bspix.data;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class BsPixDatabaseTest {

    @Test
    public void noUserIsValid() {
        assertNotNull(BsPixDatabase.NO_USER);
    }
}
