package ru.nsu.dolgushin.lab3game.model;

import ru.nsu.dolgushin.lab3game.model.gameobjects.*;
import ru.nsu.dolgushin.lab3game.model.modellistener.ModelListener;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

public class Model {
    private final ModelListener ls;
    private final Apricot player;
    private final ScoreManager scoreManager;
    private final String playerName;
    private final Field field;
    private boolean isGameEnded;
    private boolean isGamePaused;
    private class Timer extends Thread{
        private Timer(){
        }
        @Override
        public void run(){
            while(!isGameEnded) {
                long startTime =  System.currentTimeMillis();
                if(!isGamePaused){
                    field.tick();
                }
                long time = ( System.currentTimeMillis()-startTime);
                try {
                    if((11-time) < 0){
                        sleep(0);
                    }
                    else {
                        sleep(11-time);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            interrupt();
        }
    }
    public Model(ModelListener lsInput, String playerName) {
        isGameEnded = false;
        isGamePaused = false;
        this.playerName = playerName;
        ls = lsInput;
        int gravitation = 1;//(int)9.81;
        field = new Field(this,ls);
        player = new Apricot(field,400,200, 100 ,100,5, gravitation);
        new Block(field,-200,400,100,100);
        new Block(field,-200,500,100,100);
        new Block(field,-200,600,100,100);
        new Block(field,-200,700,100,100);
        new Block(field,-200,800,100,100);
        new Block(field,-100,800,100,100);
        new Block(field,0,800,100,100);
        new Block(field,100,800,100,100);
        new Block(field,200,800,100,100);
        new Block(field,300,800,100,100);
        new Block(field,400,800,100,100);
        new Block(field,500,800,100,100);
        new Block(field,600,800,100,100);
        new Block(field,700,800,100,100);
        new Block(field,800,800,100,100);
        new Block(field,900,800,100,100);
        new Block(field,1000,800,100,100);
        new Block(field,1100,800,100,100);
        new Block(field,1200,800,100,100);
        new Block(field,1300,800,100,100);
        new Block(field,1400,800,100,100);
        new Block(field,1500,800,100,100);
        new Block(field,1600,800,100,100);
        new Block(field,1700,800,100,100);
        new Block(field,1800,800,100,100);
        new Block(field,1800,700,100,100);
        new Block(field,1800,600,100,100);
        new Block(field,1800,500,100,100);
        new Block(field,1800,400,100,100);
        scoreManager = new ScoreManager(field,0,0,0,0, ls);
        new SkullSpawner(field,0,0,0,0, player,scoreManager);
    }
    public void execute(){
        Timer t = new Timer();
        t.start();
    }
    public void endGame(){
        isGameEnded = true;
        String line ="";
        int  i = 0;
        URL u = this.getClass().getResource("HighScores.txt");
        try {
            assert u != null;
            try (RandomAccessFile raf = new RandomAccessFile(new File(u.toURI()),"rw")){
                while (i<11){
                    line = raf.readLine();
                    try{
                        line = raf.readLine();
                        if( line == null || Integer.parseInt(line.split(" ")[1]) < scoreManager.getScores()){
                            break;
                        }
                        i++;
                    }
                    catch (NumberFormatException ignored){
                    }
                    catch (IndexOutOfBoundsException | EOFException ex){
                        break;
                    }
                }
                if(i >= 10){
                    return;
                }
                if(line == null){
                    line = "";
                }
                long p = raf.getFilePointer()-1-line.length();
                raf.seek(p);
                byte [] data= new byte[(int)(new File(u.toString()).length()-p)];
                raf.readFully(data);
                raf.seek(p);
                raf.writeBytes(playerName + " " + scoreManager.getScores() + "\n");
                raf.write(data);
                ls.GameEnded();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getLeaderboard() {
        URL u = Model.class.getResource("/HighScores.txt");
        assert u != null;
        try {
            return new File(u.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    void pauseGame(){
        if(isGamePaused){
            ls.gameIsUnPaused();
            isGamePaused = false;
        }
        else{
            ls.gameIsPaused();
            isGamePaused = true;
        }
    }
    public void moveRightPressed(){
        player.moveRightPressed();
    }
    public void moveLeftPressed(){
        player.moveLeftPressed();
    }
    public void jumpPressed(){
        player.jumpPressed();
    }
    public void pausePressed(){
        player.pausePressed();
    }
    public void moveRightReleased(){
        player.moveRightReleased();
    }
    public void moveLeftReleased(){
        player.moveLeftReleased();
    }
    public void jumpReleased(){
        player.jumpReleased();
    }
}
