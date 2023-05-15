package ru.nsu.dolgushin.lab3game.model.gameobjects;

import ru.nsu.dolgushin.lab3game.model.Field;

public class SkullSpawner extends GameObject implements ObjectWithBehaviour{
    private int spawnSkullTimerCurrent;
    private int spawnSkullTimerTotal;
    private int spawnSkull2TimerCurrent;
    private int spawnSkull2TimerTotal;
    private final Apricot apricot;
    private final ScoreManager scoreManager;
    public SkullSpawner(Field field, int x, int y, int width, int height, Apricot apricot,ScoreManager scoreManager) {
        super(field, x, y, width, height);
        spawnSkullTimerCurrent = 0;
        spawnSkull2TimerCurrent = 0;
        spawnSkullTimerTotal = 300;
        spawnSkull2TimerTotal = 1200;
        this.apricot = apricot;
        this.scoreManager = scoreManager;
    }
    @Override
    public void behaviour() {
        if(spawnSkullTimerCurrent >= spawnSkullTimerTotal){
            int ch = (int)(Math.random()*3);
            switch (ch) {
                case (0) ->
                        new Skull(field, (int) (Math.random() * 1600), (int) (Math.random() * 100), 100, 100, apricot, scoreManager, 1);
                case (1) ->
                        new Skull(field, (int) (Math.random() * 100), (int) (Math.random() * 800), 100, 100, apricot, scoreManager, 1);
                case (2) ->
                        new Skull(field, (int) (1500 + Math.random() * 100), (int) (Math.random() * 800), 100, 100, apricot, scoreManager, 1);
            }
            spawnSkullTimerCurrent = 0;
            spawnSkullTimerTotal = (int)((double)spawnSkullTimerTotal*0.96);
        }
        if(spawnSkull2TimerCurrent >= spawnSkull2TimerTotal){
            new Skull2(field, 1600, 649, 150, 150, scoreManager, 15);
            spawnSkull2TimerCurrent = 0;
            spawnSkull2TimerTotal = (int)((double)spawnSkull2TimerTotal*0.86);
        }
        if(spawnSkullTimerTotal < 12 && spawnSkull2TimerTotal > 20){
            spawnSkull2TimerTotal = spawnSkullTimerTotal;
        }
        spawnSkullTimerCurrent++;
        spawnSkull2TimerCurrent++;
    }
}
