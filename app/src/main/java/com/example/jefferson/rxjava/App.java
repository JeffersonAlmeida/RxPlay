package com.example.jefferson.rxjava;

import android.app.Application;

import com.example.jefferson.rxjava.di.BufferComponent;
import com.example.jefferson.rxjava.di.DaggerBufferComponent;

public class App extends Application {

    private BufferComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerBufferComponent.builder().build();
    }

    public BufferComponent getComponent() {
        return component;
    }
}
