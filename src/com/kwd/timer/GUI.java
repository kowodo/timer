package com.kwd.timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {
    JFrame frame;
    static JLabel label;
    JTextField textField;
    final double fontScalingFactor = 0.6;
    int guiWidth = 280;
    int guiHeight = 30;
    int posX = 0, posY = 0;
    TimeManager timeManager;
    static Color foregroundColor = Color.BLACK;
    static Color backgroundColor = Color.LIGHT_GRAY;

    public static void alternateColor() {
      Color tmpColor = label.getForeground();
      label.setForeground(label.getBackground());
      label.setBackground(tmpColor);
    }

    public void setTimeString(String text) {
        label.setText(text);
    }

    public void widthIncrease() {
        guiWidth++;
        guiUpdateSize();
    }

    public void widthDecrease() {
        guiWidth--;
        guiUpdateSize();
    }

    public void heightIncrease() {
        guiHeight++;
        guiUpdateSize();
    }

    public void heightDecrease() {
        guiHeight--;
        guiUpdateSize();
    }

    private void guiUpdateSize() {
        frame.setSize(guiWidth, guiHeight);
        updateFont();
    }

    private void updateFont() {
        final double newFontSize = frame.getSize().height * fontScalingFactor;
        Font font = new Font("Dialog", 0, (int) newFontSize);
        System.out.println("updateFont() setting font size to: " + newFontSize);
        label.setFont(font);
        textField.setFont(font);
    }


    private void addShit() {
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                posX = e.getX();
                posY = e.getY();
            }
        });
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }
        });
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("focus owner: " + frame.getFocusOwner());
                System.out.println("most recent focus owner: " + frame.getMostRecentFocusOwner());
            }
        });
    }

    public GUI(TimeManager timeManager) {
        this.timeManager = timeManager;
        // declaration and initialization  of shit
        frame = new JFrame();
        BorderLayout layout = new BorderLayout();
        label = new JLabel("Time yet to be set.", SwingConstants.CENTER);
        textField = new JTextField("enter time here e.g: 00h 00m 00s");
        TextAdapter textAdapter = new TextAdapter(this);
        MouseMoveListener mouseMoveListener = new MouseMoveListener(frame);
        frameKeyListener guiKeyListener = new frameKeyListener(this);

        // setting properties
        frame.setLayout(layout);
        frame.setUndecorated(true);
        frame.setAlwaysOnTop(true);
        frame.setSize(guiWidth, guiHeight);
        Font font = new Font("Dialog", 0, (int) (frame.getSize().height * fontScalingFactor));
        frame.addMouseListener(mouseMoveListener);
        frame.addMouseMotionListener(mouseMoveListener);
        frame.addKeyListener(guiKeyListener);
        frame.setVisible(true);
        frame.add(label, BorderLayout.CENTER);
        frame.add(textField, BorderLayout.NORTH);

//        frame.setForeground(backgroundColor);
//        frame.setBackground(foregroundColor);
        label.setOpaque(true);
        label.setForeground(backgroundColor);
        label.setBackground(foregroundColor);

        label.setFont(font);
        System.out.println(" BG ="+ label.getBackground());
        System.out.println(" FG ="+ label.getForeground());

        textField.setVisible(false);
        textField.setFont(font);
        textField.addKeyListener(textAdapter);
//        Dimension dimension = new Dimension(guiWidth, guiHeight);
//        textField.setMinimumSize(dimension);
//        textField.setColumns(10);

        // TODO remove this
        System.out.println("------ The font is:");
        System.out.println("frame font: " + frame.getFont());
        System.out.println("textfield font: " + textField.getFont());

        System.out.println("Available fonts:");
        String fonts[] =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for (String f :
                fonts) {
            System.out.println(f);
        }

        this.addShit();

        //TODO remove this.... it is here only to ease development
        frame.setLocation(-671, 800);
        System.out.println("Frame is focusable: " + frame.isVisible());
    }

    public void toggleIO() {
        label.setVisible(!label.isVisible());
        textField.setVisible(!textField.isVisible());
        if (frame.hasFocus()) {
            textField.requestFocus();
        } else {
            frame.requestFocus();
        }
    }

}

class TextAdapter extends KeyAdapter {
    GUI gui;

    public TextAdapter(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            gui.toggleIO();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            JTextField textField = (JTextField) e.getComponent();
            gui.timeManager.setNewTime(TimeStringParser.getDurationFromString(textField.getText()));
            gui.toggleIO();
        }
    }
}

class frameKeyListener extends KeyAdapter {
    GUI gui;

    public frameKeyListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ESCAPE) {
            System.out.println("escape pressed");
            gui.toggleIO();
        }
        if (keyCode == KeyEvent.VK_Q) {
            System.exit(0);
        }
        if (keyCode == KeyEvent.VK_P) {
            gui.timeManager.pauseTime();
        }
        if (keyCode == KeyEvent.VK_R) {
            gui.timeManager.resetTime();
        }
        if (keyCode == KeyEvent.VK_S) {
            gui.timeManager.startTime();
        }
        if (keyCode == KeyEvent.VK_UP) {
            gui.heightDecrease();
        }
        if (keyCode == KeyEvent.VK_DOWN) {
            gui.heightIncrease();
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            gui.widthIncrease();
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            gui.widthDecrease();
        }
    }

}

class MouseMoveListener extends MouseAdapter implements MouseMotionListener {
    JFrame target;
    Point startDrag;

    public MouseMoveListener(JFrame target) {
        this.target = target;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        startDrag = e.getPoint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point mouseLocationOnScreen = e.getLocationOnScreen();
        Point newLocation = new Point((int) (mouseLocationOnScreen.getX() - this.startDrag.getX()),
                (int) (mouseLocationOnScreen.getY() - this.startDrag.getY()));
        this.target.setLocation(newLocation);
    }
}
