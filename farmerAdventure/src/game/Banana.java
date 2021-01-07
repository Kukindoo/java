package game;

import city.cs.engine.*;

public class Banana extends DynamicBody {
    private static int bananaCounter;
    private Shape bananaShape = new PolygonShape(0.122f,0.591f, 0.543f,0.19f, 0.547f,-0.275f, 0.143f,-0.57f, -0.234f,-0.573f, -0.693f,0.051f);
    private BodyImage image = new BodyImage("data/bananaDefault.png",1.25f);
    public Banana(World w) {
        super(w);
        SolidFixture branchFixture = new SolidFixture(this,bananaShape,250);
        addImage(image);
    }


    //
    //Getters
    //

    public static int getBananaCounter() {
        return bananaCounter;
    }

    //
    //Setters
    //


    public static void setBananaCounter(int bananaCounter) {
        Banana.bananaCounter = bananaCounter;
    }

}
