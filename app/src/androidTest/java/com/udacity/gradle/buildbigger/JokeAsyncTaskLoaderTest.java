package com.udacity.gradle.buildbigger;

import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.udacity.gradle.builditbigger.JokeAsyncTaskLoader;
import com.udacity.gradle.builditbigger.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author cmonzon
 */

@RunWith(AndroidJUnit4.class)
public class JokeAsyncTaskLoaderTest {

    final static String joke = "Teacher: Maria please point to America on the map. \n" +
            "Maria: This is it. \n" +
            "Teacher: Well done. Now class, who found America? \n" +
            "Class: Maria did. ";

    private CountDownLatch signal = null;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        signal = new CountDownLatch(1);
    }

    @After
    public void tearDown() {
        signal.countDown();
    }

    @Test
    public void testAsyncTaskLoader() throws InterruptedException {
        final JokeAsyncTaskLoader asyncTaskLoader = new JokeAsyncTaskLoader(InstrumentationRegistry.getTargetContext(), null);
        LoaderManager.LoaderCallbacks<String> callbacks = new LoaderManager.LoaderCallbacks<String>() {
            @Override
            public Loader<String> onCreateLoader(int id, Bundle args) {
                return asyncTaskLoader;
            }

            @Override
            public void onLoadFinished(Loader<String> loader, String data) {
                assertNotNull(data);
                assertEquals(data, joke);
                signal.countDown();
            }

            @Override
            public void onLoaderReset(Loader<String> loader) {

            }
        };
        activityTestRule.getActivity().getSupportLoaderManager().initLoader(0, null, callbacks);
        signal.await();
    }
}
