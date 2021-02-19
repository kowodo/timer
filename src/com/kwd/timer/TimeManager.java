package com.kwd.timer;

import java.time.Duration;
import java.time.Instant;

public class TimeManager extends Thread {
    private Duration stopWatchDuration;
    private Instant currentStartTime;
    // first variable -> sum of all uninterrupted measured durations
    // second variable -> first + currentStart till now().
    private Duration elapsedPreviousDurationsSum = Duration.ZERO;
    private DisplayTime timeDisplayer;
    private boolean isRunning;

    public void setTimeDisplayer(DisplayTime timeDisplayer) {
        this.timeDisplayer = timeDisplayer;
    }

    public void setStopWatchDuration(Duration stopWatchDuration) {
        System.out.println("Stop watch duration set to: " + stopWatchDuration);
        this.stopWatchDuration = stopWatchDuration;
    }

    public void run() {
        Duration elapsedPreviousDurationsPlusCurrentProgress;
        System.out.println("Timer thread started");
        if (null == this.timeDisplayer) {
            this.timeDisplayer = new ConsoleTimeDisplayer();
        }
        String previousTimeString = "";
        while (true) {
            if (isRunning) {
                elapsedPreviousDurationsPlusCurrentProgress = elapsedPreviousDurationsSum.plus(Duration.between(currentStartTime, Instant.now()));
                String timeString = createTimeString(elapsedPreviousDurationsPlusCurrentProgress, true);
                if (!previousTimeString.equals(timeString)) {
                    timeDisplayer.showTime(timeString);
                }
                previousTimeString = timeString;
                if (elapsedPreviousDurationsPlusCurrentProgress.compareTo(stopWatchDuration) >= 0) {
                    isRunning = false;
                    timeDisplayer.showTime(timeString + " DONE");
                }
            }
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // eighter showing elapsed time
    // or
    // stopWatchDuration minus elapsed.
    public String createTimeString(Duration elapsed, boolean isCountdown) {
        Duration timeToShow;
        if (isCountdown) {
            timeToShow = stopWatchDuration.minus(elapsed);
        } else {
            timeToShow = elapsed;
        }
        return String.format("%02d:%02d:%02d", timeToShow.toHoursPart(), timeToShow.toMinutesPart(), timeToShow.toSecondsPart() > 0 ? timeToShow.toSecondsPart() : 0);
    }

    public void startTime() {
        System.out.println("Timer started");
        currentStartTime = Instant.now();
        isRunning = true;
    }

    public void pauseTime() {
        elapsedPreviousDurationsSum = elapsedPreviousDurationsSum.plus(Duration.between(currentStartTime, Instant.now()));
        isRunning = false;
    }

    public void resetTime() {
        System.out.println("time reset");
        elapsedPreviousDurationsSum = Duration.ZERO;
        isRunning = false;
        timeDisplayer.showTime(createTimeString(elapsedPreviousDurationsSum, true));
    }

    public void setNewTime(Duration newTime) {
        System.out.println("setting new time");
        setStopWatchDuration(newTime);
        elapsedPreviousDurationsSum = Duration.ZERO;
        isRunning = false;
        timeDisplayer.showTime(createTimeString(elapsedPreviousDurationsSum, true));
    }

}
