package com.example.subby;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class AppInputOutputTest {

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void launchAddSubActivity() {
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.addSubName)).check(matches(isDisplayed()));
    }

    @Test
    public void createAndDeleteNewSubscription() {
        String[] myArray =
                mActivityRule.getActivity().getResources()
                        .getStringArray(R.array.color_labels_array);

        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.addSubName)).perform(typeText("Test Subscription"));
        onView(withId(R.id.addSubPrice)).perform(typeText("12345.67"));
        onView(withId(R.id.addSubNote)).perform(typeText("This is a test note"));
        onView(withId(R.id.addSubColor)).perform(click());
        onData(is("Blue")).perform(click());
        onView(withId(R.id.saveSub)).perform(click());
        onView(withText("Test Subscription")).check(matches(withText("Test Subscription")));

        onView(withText("Test Subscription")).perform(click());
        onView(withId(R.id.deleteSubs)).perform(click());
        onView(withText("OK")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
        onView(withText("Test Subscription")).check(doesNotExist());
    }

    @Test
    public void createAndSwipeSubscription() {
        String[] myArray =
                mActivityRule.getActivity().getResources()
                        .getStringArray(R.array.color_labels_array);

        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.addSubName)).perform(typeText("Other Subscription"));
        onView(withId(R.id.addSubPrice)).perform(typeText("999999"));
        onView(withId(R.id.addSubNote)).perform(typeText("This is another test subscription"));
        onView(withId(R.id.addSubColor)).perform(click());
        onData(is("Yellow")).perform(click());
        onView(withId(R.id.saveSub)).perform(click());
        onView(withText("Other Subscription")).check(matches(withText("Other Subscription")));

        onView(withText("Other Subscription")).perform(swipeRight());
        onView(withText("Other Subscription")).check(doesNotExist());
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.subby", appContext.getPackageName());
    }
}