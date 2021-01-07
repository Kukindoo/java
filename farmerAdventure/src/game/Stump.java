package game;

import city.cs.engine.*;

public class Stump extends StaticBody {
    private int stumpCounter;
    private Shape stumpShape = new PolygonShape(-0.36f,-1.44f, -0.62f,-1.83f, 0.66f,-1.83f);
    private BodyImage image = new BodyImage("data/stumpDefault.png",4.25f);
    public Stump(World w) {
        super(w);
        SolidFixture stumpFixture = new SolidFixture(this, stumpShape, 2500);
        addImage(image);
    }
    {
        stumpCounter++;
    }
}
