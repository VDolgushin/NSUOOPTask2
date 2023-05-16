package ru.nsu.dolgushin.lab3game.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AboutListener implements ActionListener {
    private final JFrameMenu jframe;
    private JPanel p;
    public  AboutListener(JFrameMenu jframe){
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
        JLabel about3 = new JLabel("Apricot Garden... Such beautiful and peaceful place...");
        JLabel about4 = new JLabel("But today death and murder have come to this land!");
        JLabel about1 = new JLabel("Souls of the dead should not desecrate this glorious space!");
        JLabel about2 = new JLabel("Raise your holy glock and send them back to limbo!");
        JLabel emptyString = new JLabel("         ");
        JLabel aboutControls = new JLabel("Controls: move and jump by arrows, aim by mouse");
        JButton backButton = new JButton("BACK");
        backButton.setVisible(true);
        about1.setForeground(Color.BLACK);
        about1.setFont(new Font("IMPACT", Font.PLAIN, 60));
        about1.setVisible(true);
        about2.setForeground(Color.BLACK);
        about2.setFont(new Font("IMPACT", Font.PLAIN, 60));
        about2.setVisible(true);
        about3.setForeground(Color.BLACK);
        about3.setFont(new Font("IMPACT", Font.PLAIN, 60));
        about3.setVisible(true);
        about4.setForeground(Color.BLACK);
        about4.setFont(new Font("IMPACT", Font.PLAIN, 60));
        about4.setVisible(true);
        emptyString.setForeground(Color.BLACK);
        emptyString.setFont(new Font("IMPACT", Font.PLAIN, 120));
        emptyString.setVisible(true);
        aboutControls.setForeground(Color.BLACK);
        aboutControls.setFont(new Font("IMPACT", Font.PLAIN, 60));
        aboutControls.setVisible(true);
        p.add(about3);
        p.add(about4);
        p.add(about1);
        p.add(about2);
        p.add(emptyString);
        p.add(aboutControls);
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
