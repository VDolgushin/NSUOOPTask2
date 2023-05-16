package ru.nsu.dolgushin.lab3game.view;

import ru.nsu.dolgushin.lab3game.controller.SimpleController;
import ru.nsu.dolgushin.lab3game.model.Model;
import ru.nsu.dolgushin.lab3game.model.modellistener.ModelListener;
import ru.nsu.dolgushin.lab3game.model.modellistener.VisibleGameObjectViewInfo;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;

public class JFrameGame extends JFrame implements ModelListener {

    private final HashMap<Integer,JLabel> visibleObjectsMap;
    private final JPanel panel;
    private final JLayeredPane lp;
    private final JLabel scores;
    private final JLabel playerHP;
    private final JLabel stopped;
    private final JLabel background;
    private final Clip clip;
    public JFrameGame(String playerName) {
        super("window");
        visibleObjectsMap = new HashMap<>();
        lp = new JLayeredPane();
        panel = new JPanel(null);
        panel.setBounds(0,0,1600,900);

        scores = new JLabel("Score: "+0);
        scores.setBounds(0,10,200,100);
        scores.setForeground(Color.RED);
        scores.setFont(new Font("IMPACT", Font.PLAIN, 40));
        scores.setVisible(true);
        lp.add(scores,JLayeredPane.POPUP_LAYER);

        playerHP = new JLabel("HP: "+0);
        playerHP.setBounds(1500,10,200,100);
        playerHP.setForeground(Color.BLACK);
        playerHP.setFont(new Font("IMPACT", Font.PLAIN, 40));
        playerHP.setVisible(true);
        lp.add(playerHP,JLayeredPane.POPUP_LAYER);

        stopped = new JLabel("PAUSED");
        stopped.setBounds(0,0,1600,900);
        stopped.setForeground(Color.BLACK);
        stopped.setFont(new Font("IMPACT", Font.PLAIN, 512));
        stopped.setVisible(false);
        lp.add(stopped,JLayeredPane.DRAG_LAYER);

        JPanel bgPanel = new JPanel(null);
        try {
            URL u = this.getClass().getResource("sprites/ApricotGarden.png");
            assert u != null;
            background = new JLabel(new ImageIcon(ImageIO.read(u)));
            background.setBounds(0,0,1600,900);
            bgPanel.setBounds(0,0,1600,900);
            bgPanel.add(background);
            lp.add(bgPanel,JLayeredPane.DEFAULT_LAYER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1600, 900);
        setLayeredPane(lp);
        Model model = new Model(this,playerName);
        addKeyListener(new SimpleController(model));
        URL u = this.getClass().getResource("sound/SoundTrack.wav");
        try {
            assert u != null;
            try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(u)){
                    clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
            }
        }
        catch (LineUnavailableException ex) {
            throw new RuntimeException();
        }catch (UnsupportedAudioFileException | IOException e) {
            throw new RuntimeException(e);
        }
        model.execute();
        setVisible(true);
    }

    @Override
    public void addObject(VisibleGameObjectViewInfo obj) {
        try {
            URL u = this.getClass().getResource("sprites/" + obj.getClassName() + ".png");
            ImageIcon image = new ImageIcon(ImageIO.read(Objects.requireNonNull(u)));
            JLabel objView = new JLabel(image);
            visibleObjectsMap.put(obj.getId(), objView);
            objView.setBounds(obj.getX(), obj.getY(), obj.getMaskWidth(), obj.getMaskHeight());
            lp.add(objView,JLayeredPane.MODAL_LAYER);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeObject(int id) {
        visibleObjectsMap.get(id).setVisible(false);
        visibleObjectsMap.remove(id);
    }

    @Override
    public void notifyCords(int id, int x,int y) {
        visibleObjectsMap.get(id).setBounds(x,y,visibleObjectsMap.get(id).getWidth(),visibleObjectsMap.get(id).getHeight());
    }

    @Override
    public void GameEnded() {
        if(clip != null){
            clip.stop();
            clip.close();
        }
        setVisible(false);
        dispose();
        new JFrameMenu();
    }

    @Override
    public void notifyScore(int score) {
        scores.setText("Score: "+score);
    }

    @Override
    public void gameIsPaused() {
        stopped.setVisible(true);
        clip.stop();
    }

    @Override
    public void gameIsUnPaused() {
        stopped.setVisible(false);
        clip.start();
    }

    @Override
    public void notifyPlayerHP(int hp) {
        playerHP.setText("HP: "+hp);
    }
}
