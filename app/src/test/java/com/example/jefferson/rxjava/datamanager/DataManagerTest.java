package com.example.jefferson.rxjava.datamanager;

import com.example.jefferson.rxjava.model.WordCounter;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertEquals;

public class DataManagerTest {

    private DataManager dataManager;

    @Before
    public void setUp(){

        StringReader in = new StringReader("We all live in a yellow submarine\n" +
                "A yellow submarine, yellow s...");

        BufferedReader bufferedReader = new BufferedReader(in);
        Observable<BufferedReader> readerObservable = Observable.just(bufferedReader);

        dataManager = new DataManager(readerObservable);
    }

    @Test
    public void fetchCharactersFromTheWeb() throws Exception {
        Observable<List<Character>> observable = dataManager.trueCaller10thCharacterRequest();

        TestSubscriber testSubscriber = new TestSubscriber();
        observable.subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();

        List<List<Character>> onNextEvents = testSubscriber.getOnNextEvents();
        System.out.println(onNextEvents);

        List<Character> actual = onNextEvents.get(0);

        System.out.println(actual);

        assertEquals(Character.valueOf('W'), actual.get(0));

        assertEquals("[W, e, a, l, l, l, i, v, e, i]".trim(), actual.toString().trim());

    }

    @Test
    public void fetchNCharactersFromTheWeb() throws Exception {

        Observable<List<List<Character>>> observable = dataManager.trueCallerEvery10thCharacterRequest();

        TestSubscriber testSubscriber = new TestSubscriber();
        observable.subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();

        List onNextEvents = testSubscriber.getOnNextEvents();
        System.out.println(onNextEvents);

        List<List<Character>> list = (List<List<Character>>) onNextEvents.get(0);

        assertEquals("[W, e, a, l, l, l, i, v, e, i]", list.get(0).toString());

        List<Character> lastList = list.get(list.size() - 1);

        assertEquals(4, lastList.size());

    }

    @Test
    public void countWordsFromTheWebWithMap() throws Exception {
        Observable<Map<String, WordCounter>> observable = dataManager.trueCallerWordCounterRequest();

        TestSubscriber testSubscriber = new TestSubscriber();
        observable.subscribe(testSubscriber);

        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();

        List<Map<String, WordCounter>> list = testSubscriber.getOnNextEvents();
        WordCounter wordCounter = list.get(0).get("submarine");

        System.out.println(wordCounter);

        assertEquals((Integer) 2, wordCounter.getCount());

        WordCounter yellow = list.get(0).get("yellow");

        assertEquals((Integer) 3, yellow.getCount());

    }

}