package ru.nsu.dolgushin.lab3game.view;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextListener implements ActionListener {
    public static final String ENTER_COMMAND = "enter";
    private final JFrame frame;

    private final Document document;

    public TextListener(Document document, JFrame frame){
        this.document = document;
        this.frame = frame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().endsWith(ENTER_COMMAND)) {
            try {
                String playerName = document.getText(0, document.getLength());
                frame.setVisible(false);
                frame.dispose();
                new JFrameGame(playerName);
            } catch (NumberFormatException ignored) {
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        }
    }
}

