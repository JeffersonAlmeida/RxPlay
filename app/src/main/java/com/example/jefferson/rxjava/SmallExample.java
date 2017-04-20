package com.example.jefferson.rxjava;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SmallExample {

    private static final String TAG = "TAG";

    private static final Integer LIMIT = 3;

    public static void doIt(){
        createObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        int i = Integer.parseInt(s);
                        return ( i % 2 ) == 0;
                    }
                })
                .take(LIMIT)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, "onNext: " + s );
                    }
                });
    }

    private static Observable<String> createObservable(){
        return Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {
                for ( int i = 0 ; i < 10; i++ ){
                    subscriber.onNext(Integer.toString(i));
                }
                subscriber.onCompleted();
            }
        });
    }
}
