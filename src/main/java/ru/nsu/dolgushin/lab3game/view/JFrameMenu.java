package ru.nsu.dolgushin.lab3game.view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class JFrameMenu extends JFrame {
    private final JPanel p;
    public JFrameMenu(){
        this.setSize(1600, 900);
        p= new JPanel();
        JButton exit = new JButton("Exit");
        JButton about = new JButton("About");
        JButton newGame = new JButton("New Game");
        JButton highScores = new JButton("High Scores");
        p.add(exit);
        p.add(about);
        p.add(newGame);
        p.add(highScores);
        add(p);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ActionListener exitListener = e -> {
            JFrameMenu.this.setVisible(false);
            JFrameMenu.this.dispose();
        };
        exit.addActionListener(exitListener);
        newGame.addActionListener(new NewGameListener(p,this));
        about.addActionListener(new AboutListener(this));
        highScores.addActionListener(new LeaderBoardListener(this));
    }
    public JPanel getJPanel(){
        return p;
    }
}
