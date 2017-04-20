package com.example.jefferson.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;

public class MainActivity extends AppCompatActivity {

    private String TAG = "teste";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SmallExample.doIt();

        ReadFromTheWeb.doIt();

        Observable.just("a", "b", "c", "a", "a", "z", "b")
                .groupBy(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s;
                    }
                })
                .flatMap(new Func1<GroupedObservable<String, String>, Observable<Count>>() {
                    @Override
                    public Observable<Count> call(GroupedObservable<String, String> group) {
                        return group
                                .scan(new Count(group.getKey(), 0), (count, s) -> count.increment())
                                .last();
                    }
                })
                .subscribe(new Subscriber<Count>() {
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
                Log.e(TAG, "onNext: " + count );
            }
        });



    }

    private static Observable<Count> count(GroupedObservable<String, String> group) {
        return group.scan(new Count(group.getKey(), 0), new Func2<Count, String, Count>() {
            @Override
            public Count call(Count count, String s) {
                count.increment();
                return count;
            }
        });
    }
}
