package com.example.jefferson.rxjava.presenter;


import com.example.jefferson.rxjava.base.BasePresenter;
import com.example.jefferson.rxjava.contract.MainContract;
import com.example.jefferson.rxjava.datamanager.DataManager;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter
        extends BasePresenter<MainContract.MainView> implements MainContract.IMainPresenter {

    private final DataManager dataManager;

    @Inject
    public MainPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void requestData() {
        trueCallerWordCounterRequest();
        trueCaller10thCharacterRequest();
        trueCallerEvery10thCharacterRequest();
    }

    private void trueCallerWordCounterRequest() {
        getView().setLoadingColor(android.R.color.holo_orange_dark);
        dataManager.trueCallerWordCounterRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> getView().showLoading())
                .doOnCompleted(() -> getView().hideLoading())
                .doOnError( error -> getView().hideLoading() )
                .subscribe( wordCounter -> getView().trueCallerWordCounterRequest(wordCounter),
                        e -> getView().showError(e.getMessage()) );
    }

    private void trueCaller10thCharacterRequest() {
        getView().setLoadingColor(android.R.color.holo_blue_dark);
        dataManager.trueCaller10thCharacterRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> getView().showLoading())
                .doOnCompleted(() -> getView().hideLoading())
                .doOnError( error -> getView().hideLoading() )
                .subscribe( character -> getView().trueCaller10thCharacterRequest(character),
                        e -> getView().showError(e.getMessage()) );
    }

    private void trueCallerEvery10thCharacterRequest() {
        getView().setLoadingColor(android.R.color.holo_green_dark);
        dataManager.trueCallerEvery10thCharacterRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> getView().showLoading())
                .doOnCompleted(() -> getView().hideLoading())
                .doOnError( error -> getView().hideLoading() )
                .subscribe( characters -> getView().trueCallerEvery10thCharacterRequest(characters),
                        e -> getView().showError(e.getMessage()) );
    }
}
