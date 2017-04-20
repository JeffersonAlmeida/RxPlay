package com.example.jefferson.rxjava.view;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jefferson.rxjava.App;
import com.example.jefferson.rxjava.R;
import com.example.jefferson.rxjava.contract.MainContract;
import com.example.jefferson.rxjava.model.WordCounter;
import com.example.jefferson.rxjava.presenter.MainPresenter;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    private ProgressBar progressBar;

    private TextView result;

    @Inject
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((App) getApplication()).getComponent().inject(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        result = (TextView) findViewById(R.id.text_result);

        mainPresenter.attachView(this);
        mainPresenter.requestData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

    public void onClickRunIt(View view){
        cleanResult();
        mainPresenter.requestData();
    }

    private void cleanResult() {
        result.setText("");
    }

    @Override
    public void showLoading() {
        if ( !isLoadingVisible() )
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        if ( isLoadingVisible() )
            progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setLoadingColor(int c) {
        int color = ContextCompat.getColor(this, c);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(color, PorterDuff.Mode.SRC_IN );
    }

    @Override
    public void trueCaller10thCharacterRequest(List<Character> characters) {
        this.result.append(getString(R.string.trueCaller10thCharacterRequest));
        this.result.append(characters+"");
    }

    @Override
    public void trueCallerEvery10thCharacterRequest(List<List<Character>> characters) {
        this.result.append(getString(R.string.trueCallerEvery10thCharacterRequest));
        this.result.append(characters+"");
    }

    @Override
    public void trueCallerWordCounterRequest(Map<String, WordCounter> map) {
        this.result.append(getString(R.string.trueCallerWordCounterRequest));
        this.result.append(map.toString());
    }

    private boolean isLoadingVisible(){
        return progressBar.getVisibility() == View.VISIBLE;
    }

    @Override
    public void showError(String error) {
            this.result.append(error+ "\n");
    }

}
