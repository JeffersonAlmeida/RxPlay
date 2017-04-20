package com.example.jefferson.rxjava.di;

import com.example.jefferson.rxjava.view.MainActivityTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component ( modules = MockBufferModule.class )
public interface MockBufferComponent extends BufferComponent {

    void inject(MainActivityTest mainActivityTest);

}
