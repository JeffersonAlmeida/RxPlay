package com.example.jefferson.rxjava.contract;


import com.example.jefferson.rxjava.base.MvpView;
import com.example.jefferson.rxjava.model.WordCounter;

import java.util.List;
import java.util.Map;

public class MainContract {

    public interface MainView extends MvpView {
        void showLoading();
        void hideLoading();
        void setLoadingColor(int color);

        void trueCaller10thCharacterRequest(List<Character> characters);
        void trueCallerEvery10thCharacterRequest(List<List<Character>> characters);
        void trueCallerWordCounterRequest(Map<String, WordCounter> map);

        void showError(String error);
    }

    public interface IMainPresenter {
        void requestData();
    }

}
