package com.example.jefferson.rxjava;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;

public class ReadFromTheWeb {

    private static final String TAG = "READ_FROM_THE_WEB";

    public static void doIt() {

        createObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return Observable.from(s.split("\\W+"));
                    }
                })
                .groupBy(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s.toLowerCase();
                    }
                })
                .flatMap(new Func1<GroupedObservable<String, String>, Observable<Count>>() {
                    @Override
                    public Observable<Count> call(GroupedObservable<String, String> group) {
                        return group
                                .scan(new Count(group.getKey(), 0), (count, s) -> count.increment())
                                .last();
                    }
                }).subscribe(new Subscriber<Count>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onNext(Count count) {
                Log.e(TAG, "onNext: " + count);
            }
        });


    }

    private static Observable<String> createObservable(){
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    URL oracle = new URL(Web.URL);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(oracle.openStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        if ( !inputLine.isEmpty() ) {
                            subscriber.onNext(inputLine);
                        }
                    }
                    in.close();
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
