package ru.nsu.dolgushin.lab3game.view;

import ru.nsu.dolgushin.lab3game.model.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class LeaderBoardListener implements ActionListener {
    private final JFrameMenu jframe;
    private JPanel p;
    public  LeaderBoardListener(JFrameMenu jframe){
        this.jframe= jframe;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(p!=null){
            jframe.getJPanel().setVisible(false);
            p.setVisible(true);
            jframe.setVisible(true);
            return;
        }
        p = new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        jframe.getJPanel().setVisible(false);
        JButton backButton = new JButton("BACK");
        backButton.setVisible(true);

        File file = Model.getLeaderboard();
        try(BufferedReader bf = new BufferedReader(new FileReader(file))){
            p.setLayout(new BoxLayout( p,BoxLayout.Y_AXIS));
            String line = bf.readLine();
            while( line != null){
                JLabel scoreL = new JLabel(line);
                scoreL.setForeground(Color.BLACK);
                scoreL.setFont(new Font("IMPACT", Font.PLAIN, 30));
                scoreL.setVisible(true);
                p.add(scoreL);
                line = bf.readLine();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        p.add(backButton);
        p.setVisible(true);
        backButton.addActionListener(e1 -> {
            p.setVisible(false);
            jframe.getJPanel().setVisible(true);
        });
        jframe.add(p);
        jframe.setVisible(true);
    }
}
