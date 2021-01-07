package game;

import city.cs.engine.*;

public class Star extends DynamicBody {
    private static int starCounter = 0;
    private static int farmerLevelNeeded;
    private static Shape starTopMiddleShape = new PolygonShape(-0.007f,0.727f, -0.274f,0.274f, 0.264f,0.258f
    );
    private static Shape starTopRightShape = new PolygonShape(0.334f,-0.08f, 0.701f,0.211f, 0.274f,0.261f
    );
    private static Shape starTopLeftShape = new PolygonShape(-0.321f,-0.106f, -0.737f,0.241f, -0.258f,0.254f
    );
    private static Shape starBottomRightShape = new PolygonShape(0.334f,-0.08f, 0.522f,-0.622f, 0.007f,-0.308f
    );
    private static Shape starBottomLeftShape = new PolygonShape(-0.007f,-0.324f, -0.512f,-0.638f, -0.321f,-0.106f
    );
    private static Shape starMiddleShape = new PolygonShape(-0.321f,-0.106f, -0.258f,0.254f, 0.261f,0.241f, 0.334f,-0.139f, -0.007f,-0.311f
    );
    private static BodyImage image = new BodyImage("data/starDefault.png",1.5f);
    public Star(World world) {
        super(world);
        SolidFixture starTopMiddleFixture = new SolidFixture(this, starTopMiddleShape,15);
        SolidFixture starTopRightFixture = new SolidFixture(this, starTopRightShape,15);
        SolidFixture starTopLeftFixture = new SolidFixture(this, starTopLeftShape,15);
        SolidFixture starBottomRightFixture = new SolidFixture(this, starBottomRightShape,1000);
        SolidFixture starBottomLeftFixture = new SolidFixture(this, starBottomLeftShape,1000);
        SolidFixture starMiddleFixture = new SolidFixture(this, starMiddleShape,500);
        addImage(image);

        farmerLevelNeeded = 4;
    }
    {
        starCounter++;
    }

    //
    //setters
    //

    public static void setStarCounter(int starCounter) {
        Star.starCounter = starCounter;
    }

    //
    //Getters
    //

    public static int getStarCounter() {
        return starCounter;
    }

    public static int getFarmerLevelNeeded() {
        return farmerLevelNeeded + Flower.getFlowerCounter();
    }
}
