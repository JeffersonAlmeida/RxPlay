package com.example.jefferson.rxjava.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WordCounter {

    private String word;
    private Integer count;

    public WordCounter increment(){
        this.count++;
        return this;
    }

    @Override
    public String toString() {
        return count + "";
    }
}
