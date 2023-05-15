package ru.nsu.dolgushin.lab3game.view;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameListener implements ActionListener {
    private final JPanel p;
    private final JFrame frame;

    public NewGameListener(JPanel p,JFrame frame){
        this.p = p;
        this.frame =frame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JPanel p2 = new JPanel();
        Document textModel = new PlainDocument();
        TextListener tl= new TextListener(textModel,frame);


        JTextField textField = new JTextField(42);
        textField.setDocument(textModel);
        p2.add(textField);

        JButton button = new JButton("Enter");
        p2.add(button);
        button.setActionCommand(TextListener.ENTER_COMMAND);
        button.addActionListener(tl);
        button.setVisible(true);
        p.setVisible(false);
        p2.setVisible(true);
        frame.add(p2);
    }
}