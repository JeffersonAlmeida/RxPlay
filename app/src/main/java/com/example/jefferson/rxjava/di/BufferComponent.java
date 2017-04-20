package com.example.jefferson.rxjava.di;

import com.example.jefferson.rxjava.view.MainActivity;

import javax.inject.Singleton;

@Singleton
@dagger.Component ( modules = BufferModule.class )
public interface BufferComponent {
    void inject(MainActivity mainActivity);
}
