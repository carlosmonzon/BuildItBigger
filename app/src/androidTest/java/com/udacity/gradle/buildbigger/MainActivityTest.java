package com.udacity.gradle.buildbigger;

import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.cmonzon.jokeviewermodule.JokeViewerActivity;
import com.udacity.gradle.builditbigger.MainActivity;
import com.udacity.gradle.builditbigger.R;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * @author cmonzon
 */

public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void clickTellJoke_validateIntentExtra() {
        onView(withText(R.string.button_text)).perform(click());
        intended(hasExtraWithKey(JokeViewerActivity.JOKE_LABEL));
    }
}
