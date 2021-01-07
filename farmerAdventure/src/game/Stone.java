package game;

import city.cs.engine.*;
import city.cs.engine.Shape;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

public class Stone extends DynamicBody {
    private static int stoneCounter;
    private Shape stoneShape = new PolygonShape(-0.17f,0.093f, 0.084f,0.156f, 0.3f,-0.031f, 0.285f,-0.235f, -0.073f,-0.292f, -0.323f,-0.224f, -0.317f,-0.051f);
    private BodyImage image = new BodyImage("data/stoneDefault.png",0.75f);
    private static SoundClip stoneSound;
    public Stone(World w) {
        super(w);
        SolidFixture stoneFixture = new SolidFixture(this,stoneShape,5000);
        addImage(image);
    }
    {
        stoneCounter++;
    }
    static {
        try {
            stoneSound = new SoundClip("data/sounds/stone hitt.wav");
        }catch (UnsupportedAudioFileException| IOException| LineUnavailableException e){
            System.out.println(e);
        }
    }
    //
    //Getter
    //

    public static int getStoneCounter() {
        return stoneCounter;
    }

    public static SoundClip getStoneSound() {
        //Sound plays on hit in ObjectListener.java
        stoneSound.setVolume(Game.getGameVolume());
        return stoneSound;
    }

    //
    //Methods
    //

    //
    //Setters
    //

    public static void setStoneCounter(int stoneCounter) {
        Stone.stoneCounter = stoneCounter;
    }
}

