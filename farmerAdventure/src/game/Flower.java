
package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 *
 */
public class Flower extends DynamicBody {
    private static int flowerCounter;
    private static Shape flowerHeadShape = new PolygonShape(
            -0.221f,-0.088f, -0.161f,-0.324f, 0.121f,-0.326f, 0.197f,0.036f, 0.177f,0.3f, 0.065f,0.424f, -0.101f,0.422f, -0.221f,0.308f);
    private static Shape flowerPotShape = new PolygonShape(-0.165f,-0.322f, 0.127f,-0.324f, 0.147f,-0.496f, -0.195f,-0.498f);
    private static BodyImage image = new BodyImage("data/flowerDefault.png", 1f);
    private int farmerLevelNeeded;
    private boolean pickedUp;
    private static SoundClip flowerSound;
    public Flower(World world) {
        super(world);
        SolidFixture flowerHeadFixture = new SolidFixture(this,flowerHeadShape,5);
        SolidFixture flowerPotFixture = new SolidFixture(this,flowerPotShape,5000);
        addImage(image);
        flowerPotFixture.setFriction(1);
        farmerLevelNeeded = 1;
    }
    {
        flowerCounter++;
    }
    static {
        try {
            flowerSound = new SoundClip("data/sounds/Pickup.wav");

        }catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
            System.out.println(e);
        }

    }
    //
    //Getters
    //

    public int getFarmerLevelNeeded() {
        return farmerLevelNeeded;
    }

    public static int getFlowerCounter() {
        return flowerCounter;
    }

    //
    // Methods
    //

    @Override
    public void destroy() {
        flowerSound.setVolume(Game.getGameVolume());
        if(!Game.isFsxMuted())
        flowerSound.play();
        super.destroy();
    }


    //
    //Setter
    //

    public void setFarmerLevelNeeded(int farmerLevelNeeded) {
        this.farmerLevelNeeded = farmerLevelNeeded;
    }

    public static void setFlowerHeadShape(Shape flowerHeadShape) {
        Flower.flowerHeadShape = flowerHeadShape;
    }

    public static void setFlowerPotShape(Shape flowerPotShape) {
        Flower.flowerPotShape = flowerPotShape;
    }

    public static void setImage(BodyImage image) {
        Flower.image = image;
    }

    public static void setFlowerCounter(int flowerCounter) {
        Flower.flowerCounter = flowerCounter;
    }
}