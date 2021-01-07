package game;

import city.cs.engine.*;
import city.cs.engine.Shape;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

public class Tree extends DynamicBody {
    private static int treeCounter;
    private Shape treeCrownShape;
    private Shape treeTrunkShape;
    private BodyImage image = new BodyImage("data/treeDefault.png",4.25f);
    private BodyImage imageWithBanana = new BodyImage("data/treeBanana.png",4.25f);
    private int FarmerLevelNeeded;
    private boolean bananaPresence;
    private static int levelNeeded = 6;
    private static String itemNeeded = "axe";
    private static SoundClip treeSound;
    public Tree(World world) {
        super(world);
        treeCrownShape = new PolygonShape(-0.82f,-0.75f, -1.3f,-0.71f, -1.3f,0.94f, -0.7f,1.46f, 0.61f,1.45f, 1.73f,0.54f, 1.18f,-0.8f
        );
        treeTrunkShape = new PolygonShape(-0.84f,-0.72f, 0.8f,-0.76f, 0.68f,-1.82f, -0.55f,-1.82f
        );
        FarmerLevelNeeded = 4;
        SolidFixture treeCrownFixture = new SolidFixture(this,treeCrownShape,5000);
        SolidFixture treeTrunkFixture = new SolidFixture(this,treeTrunkShape,50000);
        addImage(image);
        treeTrunkFixture.setFriction(1);
    }
    {
        treeCounter++;
    }
    static {
        try{
            treeSound = new SoundClip("data/sounds/Hit_tree.wav");
        }catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
            System.out.println(e);
        }
    }

    //
    //Methods
    //


    @Override
    public void destroy() {
        if(!Game.isFsxMuted())
            treeSound.play();
        super.destroy();
    }

    public void addBanana(){
        this.removeAllImages();
        this.addImage(imageWithBanana);
        this.bananaPresence = true;
    }
    public void removeBanana(){
        this.removeAllImages();
        this.addImage(image);
        this.bananaPresence = false;
    }

    //
    //Getters
    //
    public int getFarmerLevelNeeded() {
        return FarmerLevelNeeded;
    }

    public static int getTreeCounter() {
        return treeCounter;
    }

    public boolean isBananaPresence() {
        return bananaPresence;
    }


    //
    //Setters
    //

    public static void setTreeCounter(int treeCounter) {
        Tree.treeCounter = treeCounter;
    }

    public void setBananaPresence(boolean bananaPresence) {
        this.bananaPresence = bananaPresence;
    }


}
