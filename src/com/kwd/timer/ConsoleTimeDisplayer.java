package com.kwd.timer;

public class ConsoleTimeDisplayer implements DisplayTime{
    @Override
    public void showTime(String timeString) {
        System.out.println(timeString);
    }
}
