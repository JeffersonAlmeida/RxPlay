package com.example.jefferson.rxjava.datamanager;


import com.example.jefferson.rxjava.model.WordCounter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.observables.GroupedObservable;

public class DataManager {

    public static final int AMOUNT = 10;

    private final Observable<BufferedReader> bufferedReader;

    @Inject
    public DataManager(Observable<BufferedReader> bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    /**
     * Truecaller10thCharacterRequest:
     *
     * @return
     */
    public Observable<List<Character>> trueCaller10thCharacterRequest(){
        return Observable.defer(() -> fetchLines()
                .flatMap(DataManager.this::fetchCharacters)
                .take(AMOUNT))
                .toList();
    }

    /**
     * TruecallerEvery10thCharacterRequest:
     * @return
     */
    public Observable<List<List<Character>>> trueCallerEvery10thCharacterRequest(){
        return Observable.defer(() -> fetchLines()
                .flatMap(DataManager.this::fetchCharacters))
                .buffer(AMOUNT)
                .toList();
    }

    /**
     * TruecallerWordCounterRequest:
     * @return
     */
    public Observable<Map<String, WordCounter>> trueCallerWordCounterRequest(){
        return Observable.defer(() -> fetchLines()
                .flatMap(DataManager.this::fetchWords)
                .groupBy(String::toLowerCase)
                .flatMap(DataManager.this::countWords))
                 .toMap(WordCounter::getWord);
    }

    private Observable<WordCounter> countWords(GroupedObservable<String, String> group){
        return group
                .scan(new WordCounter(group.getKey(), 0), (wordCounter, s) -> wordCounter.increment())
                .last();
    }

    private Observable<String> fetchWords(String lines){
        return Observable.from(lines.split("[^a-zA-Z]+"));
    }

    private Observable<Character> fetchCharacters(String lines){
        return Observable.create(subscriber -> {
            for (int i = 0 ; i < lines.length() ; i++ ){
                Character c = lines.charAt(i);
                if ( !c.equals(' ') ) {
                    subscriber.onNext(c);
                }
            }
            subscriber.onCompleted();
        });
    }

    private Observable<String> fetchLines(){
        return Observable.create(subscriber -> {
            try {
                String inputLine;
                BufferedReader br = bufferedReader.toBlocking().first();
                while ((inputLine = br.readLine()) != null) {
                    if ( !inputLine.isEmpty() ) {
                        subscriber.onNext(inputLine);
                    }
                }
                subscriber.onCompleted();
            } catch (IOException e) {
                subscriber.onError(e);
            }
        });
    }
}
