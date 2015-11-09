package com.tvoyagryvnia.bean;


public class Pair <T,N> {
    public T first;
    public N second;

    public Pair(T f, N s){
        first = f;
        second = s;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public N getSecond() {
        return second;
    }

    public void setSecond(N second) {
        this.second = second;
    }
}
