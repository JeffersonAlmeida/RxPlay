package com.example.jefferson.rxjava.base;


public interface MvpPresenter<T extends MvpView> {

    void attachView(T view);
    void detachView();

}
