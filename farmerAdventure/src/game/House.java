package game;

import city.cs.engine.*;

public class House extends DynamicBody {
    private Shape houseShape = new PolygonShape(-3.24f,-4.27f, -3.28f,-0.47f, -1.32f,3.33f, 0.77f,3.34f, 2.64f,-0.54f, 2.46f,-4.27f);
    private Shape houseFloor = new PolygonShape(2.46f,-4.36f, 0.64f,-4.35f, -3.21f,-4.36f);
    private Shape houseRoof = new PolygonShape(2.5f,-1.61f, -3.26f,-1.59f, -3.47f,-0.38f, -1.33f,3.27f, 0.91f,3.24f, 2.81f,-0.02f);
    private BodyImage image = new BodyImage("data/houseDefault.png",8.75f);
    public House(World w) {
        super(w);
        SolidFixture houseFloorFixture = new SolidFixture(this,houseFloor,5000) ;
        SolidFixture houseRoofFixture = new SolidFixture(this,houseRoof,5000);
        GhostlyFixture houseFixture = new GhostlyFixture(this,houseShape,5000);
        addImage(image);
    }
}
