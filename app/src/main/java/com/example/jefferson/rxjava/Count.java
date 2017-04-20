package com.example.jefferson.rxjava;

public class Count {

    private String s;
    private Integer count;

    public Count(String s, Integer count) {
        this.s = s;
        this.count = count;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Count increment(){
        this.count++;
        return this;
    }

    @Override
    public String toString() {
        return "Count{" +
                "s='" + s + '\'' +
                ", count=" + count +
                '}';
    }
}
