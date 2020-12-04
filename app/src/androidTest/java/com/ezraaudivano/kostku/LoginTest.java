package com.ezraaudivano.kostku;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginTest() {
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.btnlogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.passwordLog),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_passwordLog_layout),
                                        0),
                                0)));
        textInputEditText.perform(scrollTo(), replaceText("asdasd"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btnlogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.emailLog),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_email_layout),
                                        0),
                                0)));
        textInputEditText2.perform(scrollTo(), replaceText("ezraaudivano"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.passwordLog), withText("asdasd"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_passwordLog_layout),
                                        0),
                                0)));
        textInputEditText3.perform(scrollTo(), replaceText("asdasd"));

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.passwordLog), withText("asdasd"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_passwordLog_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.btnlogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0)));
        materialButton3.perform(scrollTo(), click());

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.emailLog), withText("ezraaudivano"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_email_layout),
                                        0),
                                0)));
        textInputEditText5.perform(scrollTo(), replaceText("ezraaudivano@gmail.com"));

        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.emailLog), withText("ezraaudivano@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_email_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText6.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.passwordLog), withText("asdasd"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_passwordLog_layout),
                                        0),
                                0)));
        textInputEditText7.perform(scrollTo(), replaceText(""));

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.passwordLog),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_passwordLog_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText8.perform(closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.btnlogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0)));
        materialButton4.perform(scrollTo(), click());

        ViewInteraction textInputEditText9 = onView(
                allOf(withId(R.id.emailLog), withText("ezraaudivano@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_email_layout),
                                        0),
                                0)));
        textInputEditText9.perform(scrollTo(), click());

        ViewInteraction textInputEditText10 = onView(
                allOf(withId(R.id.emailLog), withText("ezraaudivano@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_email_layout),
                                        0),
                                0)));
        textInputEditText10.perform(scrollTo(), click());

        ViewInteraction textInputEditText11 = onView(
                allOf(withId(R.id.emailLog), withText("ezraaudivano@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_email_layout),
                                        0),
                                0)));
        textInputEditText11.perform(scrollTo(), replaceText("jokondokondo@gmail.com"));

        ViewInteraction textInputEditText12 = onView(
                allOf(withId(R.id.emailLog), withText("jokondokondo@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_email_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText12.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText13 = onView(
                allOf(withId(R.id.passwordLog),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_passwordLog_layout),
                                        0),
                                0)));
        textInputEditText13.perform(scrollTo(), replaceText("asdasd"), closeSoftKeyboard());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.btnlogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0)));
        materialButton5.perform(scrollTo(), click());

        ViewInteraction textInputEditText14 = onView(
                allOf(withId(R.id.emailLog), withText("jokondokondo@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_email_layout),
                                        0),
                                0)));
        textInputEditText14.perform(scrollTo(), click());

        ViewInteraction textInputEditText15 = onView(
                allOf(withId(R.id.emailLog), withText("jokondokondo@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_email_layout),
                                        0),
                                0)));
        textInputEditText15.perform(scrollTo(), click());

        ViewInteraction textInputEditText16 = onView(
                allOf(withId(R.id.emailLog), withText("jokondokondo@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_email_layout),
                                        0),
                                0)));
        textInputEditText16.perform(scrollTo(), replaceText("ezraaudivano@gmail.com"));

        ViewInteraction textInputEditText17 = onView(
                allOf(withId(R.id.emailLog), withText("ezraaudivano@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_email_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText17.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText18 = onView(
                allOf(withId(R.id.passwordLog), withText("asdasd"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_passwordLog_layout),
                                        0),
                                0)));
        textInputEditText18.perform(scrollTo(), replaceText("asdasd"));

        ViewInteraction textInputEditText19 = onView(
                allOf(withId(R.id.passwordLog), withText("asdasd"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_passwordLog_layout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText19.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText20 = onView(
                allOf(withId(R.id.passwordLog), withText("asdasd"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.input_passwordLog_layout),
                                        0),
                                0)));
        textInputEditText20.perform(scrollTo(), click());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.btnlogin), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0)));
        materialButton6.perform(scrollTo(), click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
