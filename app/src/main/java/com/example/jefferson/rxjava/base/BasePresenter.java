package com.example.jefferson.rxjava.base;

import lombok.Data;

@Data
public class BasePresenter<T extends MvpView> implements MvpPresenter<T> {

    T view;

    @Override
    public void attachView(T view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }
}
