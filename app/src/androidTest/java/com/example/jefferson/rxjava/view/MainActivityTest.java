package com.example.jefferson.rxjava.view;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.example.jefferson.rxjava.App;
import com.example.jefferson.rxjava.di.MockBufferComponent;
import com.example.jefferson.rxjava.R;
import com.example.jefferson.rxjava.RxSchedulersOverrideRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.BufferedReader;

import javax.inject.Inject;

import rx.Observable;

public class MainActivityTest {

    @Inject
    Observable<BufferedReader> bufferedReader;

    public @Rule
    RxSchedulersOverrideRule overrideRule = new RxSchedulersOverrideRule();

    public @Rule
    ActivityTestRule<MainActivity> main =
            new ActivityTestRule<>(MainActivity.class, true, false);

    @Before
    public void setUp() throws Exception {
        App app = (App) InstrumentationRegistry.getTargetContext().getApplicationContext();
        MockBufferComponent component = (MockBufferComponent) app.getComponent();
        component.inject(this);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void onClickRunIt() throws Exception {
        main.launchActivity(new Intent());

        Espresso.onView(ViewMatchers.withId(R.id.button))
                .perform(ViewActions.click());

        Thread.sleep(13000);

    }

}