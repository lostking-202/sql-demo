package com.example.demo.controller;

public class SingleInstance {
    private static SingleInstance instance;
    private SingleInstance(){};
    public static SingleInstance getInstance(){
        if(instance==null){
            synchronized (SingleInstance.class){
                if(instance==null){
                    instance=new SingleInstance();
                }
            }
        }
        return instance;
    }
}
