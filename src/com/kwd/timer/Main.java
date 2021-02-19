package com.kwd.timer;

import java.time.Duration;
import java.util.Scanner;

public class Main {

    public static Duration getInput() {
        Scanner in = new Scanner(System.in);
        System.out.println("input?:");
        String line = in.nextLine();
        return TimeStringParser.getDurationFromString(line);
    }

    public static void main(String[] args) {
        TimeManager timeManager = new TimeManager();
        GUI gui = new GUI(timeManager);
//        Duration duration = Main.getInput();
//        System.out.println("From user input we got duration: " + duration);
        // end represents the start time here. Thank you a lot LordMZTE!
//        Instant end = Instant.now();
//        System.out.println(end);
//        Instant realEnd = end.plus(duration);

        GUITimeDisplayer guiTimeDisplayer = new GUITimeDisplayer(gui);

        timeManager.setTimeDisplayer(guiTimeDisplayer);
//        timeManager.setTimerDuration(duration);
        timeManager.start();
//        timeManager.startTime();

    }
}
