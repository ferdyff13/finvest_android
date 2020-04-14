package com.invisee.finvest;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.invisee.finvest.ui.activities.SignInActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Created by fajarfatur on 1/31/16.
 */
@RunWith(AndroidJUnit4.class)
public class SignInActivityTest {


    public static final String USERNAME_TO_BE_TYPED = "fajar.faturrochman@indivaragroup.com";
    public static final String PASSWORD_TO_BE_TYPED = "password123";

    @Rule
    public ActivityTestRule<SignInActivity> signInActivityActivityTestRule =
            new ActivityTestRule<>(SignInActivity.class);

    @Test
    public void login() {
        onView(withId(R.id.etUsername))
                .perform(typeText(USERNAME_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.etPassword))
                .perform(typeText(PASSWORD_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.bSignIn))
                .perform(click());
    }

}
