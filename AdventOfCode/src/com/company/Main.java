package com.company;

import com.company.Day1.FirstTask;
import com.company.Day2.Passwords;

public class Main {

    public static void main(String[] args) {
        //dayOne();
        dayTwo();


    }

    public static void dayOne(){
        FirstTask firstTask = new FirstTask();

        Integer resultFor2 = firstTask.findOutputFor2();
        Integer resultFor3 = firstTask.findOutputFor3();
    }

    public static void dayTwo(){
        Passwords pass = new Passwords();
    }
}
