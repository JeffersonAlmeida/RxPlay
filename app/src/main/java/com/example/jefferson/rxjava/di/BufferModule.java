package com.example.jefferson.rxjava.di;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.inject.Singleton;

import dagger.Provides;
import rx.Observable;

@dagger.Module
public class BufferModule {

    private static final String URL = "https://support.truecaller.com/hc/en-us";

    @Provides
    @Singleton
    Observable<BufferedReader> providesBufferedReader(){
        return Observable.create(subscriber -> {
            BufferedReader bufferedReader = null;
            try {
                URL url = new URL(URL);
                InputStreamReader in = new InputStreamReader(url.openStream());
                bufferedReader = new BufferedReader(in);
                subscriber.onNext(bufferedReader);
            } catch (IOException e) {
                subscriber.onError(e);
            }
        });
    }
}
