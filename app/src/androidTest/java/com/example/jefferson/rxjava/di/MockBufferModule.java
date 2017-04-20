package com.example.jefferson.rxjava.di;

import java.io.BufferedReader;
import java.io.StringReader;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Observable;

@Module
public class MockBufferModule {

    @Provides
    @Singleton
    Observable<BufferedReader> providesBufferedReader(){
        StringReader in = new StringReader("We all live in a yellow submarine\n" +
                "A yellow submarine, yellow s...");

        BufferedReader bufferedReader = new BufferedReader(in);
        Observable<BufferedReader> readerObservable = Observable.just(bufferedReader);
        return  readerObservable;
    }
}
