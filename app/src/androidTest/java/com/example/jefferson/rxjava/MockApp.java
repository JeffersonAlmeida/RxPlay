package com.example.jefferson.rxjava;

import com.example.jefferson.rxjava.di.BufferComponent;
import com.example.jefferson.rxjava.di.DaggerMockBufferComponent;

public class MockApp extends App {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public BufferComponent getComponent() {
        return DaggerMockBufferComponent.builder().build();
    }
}
