package com.juancoob.nanodegree.and.backingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.juancoob.nanodegree.and.backingapp.presentation.recipeList.RecipeListActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

/**
 * Instrumented test, which will test the RecipeList UI
 */
@RunWith(AndroidJUnit4.class)
public class BackingAppTest {

    private static final String RECIPE_NAME = "Brownies";
    private static final int MIN_RECIPE_COUNT = 4;
    private static final int FIRST_POSITION = 0;
    private static final int SECOND_POSITION = 1;
    private static final String RECIPE_TEXT = "Recipe Introduction";
    @Rule
    public ActivityTestRule<RecipeListActivity> mRecipeListActivityActivityTestRule =
            new ActivityTestRule<>(RecipeListActivity.class);
    private IdlingResource mIdlingResource;
    private boolean mIsTablet;

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mRecipeListActivityActivityTestRule
                .getActivity().getRecipeListPresenter().getCountingIdlingResource();

        IdlingRegistry.getInstance().register(mIdlingResource);
        mIsTablet = mRecipeListActivityActivityTestRule.getActivity().getResources().getBoolean(R.bool.tablet);
    }

    @Test
    public void checkRecipeListItems_clickRecyclerViewItem_checkRecipeDescriptionTitle() {
        onView(withId(R.id.rv_recipes)).check(matches(hasMinimumChildCount(MIN_RECIPE_COUNT)));
        onView(withId(R.id.rv_recipes)).perform(actionOnItemAtPosition(SECOND_POSITION, click()));
        onView(isAssignableFrom(Toolbar.class)).check(matches(getToolbarTitle(is(RECIPE_NAME))));
        onView(withId(R.id.rv_steps)).perform(actionOnItemAtPosition(FIRST_POSITION, click()));
        onView(withId(R.id.sepv_recipe_video)).check(matches(isDisplayed()));
        onView(withId(R.id.tv_step_description)).check(matches(withText(RECIPE_TEXT)));
        if (mIsTablet) {
            onView(withId(R.id.rv_steps)).perform(actionOnItemAtPosition(1, click()));

        } else {
            onView(withId(R.id.fab_next)).check(matches(isDisplayed()));
            onView(withId(R.id.fab_next)).perform(click());
        }
        onView(withId(R.id.sepv_recipe_video)).check(matches(not(isDisplayed())));
        onView(withId(R.id.tv_no_video)).check(matches(isDisplayed()));
    }

    /*
     * This method match the toolbar text with the given string.
     * I saw this method here: http://blog.sqisland.com/2015/05/espresso-match-toolbar-title.html
     * */

    private Matcher<? super View> getToolbarTitle(final Matcher<String> stringMatcher) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {

            @Override
            public void describeTo(Description description) {
                description.appendText("Toolbar title: ");
                stringMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(Toolbar item) {
                return stringMatcher.matches(item.getTitle());
            }
        };
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
