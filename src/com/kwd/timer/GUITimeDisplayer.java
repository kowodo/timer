package com.kwd.timer;

public class GUITimeDisplayer implements DisplayTime {
    private GUI gui;

    public GUITimeDisplayer(GUI gui) {
        System.out.println("GUI time displayer created");
        this.gui = gui;
    }

    @Override
    public void showTime(String timeString) {
        System.out.println(" sending text " + timeString);
        this.gui.setTimeString(timeString);
    }
}
