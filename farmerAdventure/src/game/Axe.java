package game;

import city.cs.engine.*;

public class Axe extends DynamicBody {
    private static int axeCounter;
    private int durability = 0;
    private static int defaultDurability = 2;
    private Shape axeHeadShape;
    private Shape axeShaftShape;
    private BodyImage image = new BodyImage("data/axeDefault.png",0.75f);

    public Axe(World w) {
        super(w);
        axeHeadShape = new PolygonShape(0.066f,0.317f, -0.078f,0.21f, -0.078f,0.146f, 0.132f,0.009f, 0.196f,0.003f, 0.228f,0.039f, 0.229f,0.14f
        );
        axeShaftShape= new PolygonShape(-0.252f,-0.24f, -0.252f,-0.178f, 0.1f,0.207f, 0.156f,0.207f, 0.159f,0.149f, -0.191f,-0.237f
        );
        SolidFixture axeHeadFixture = new SolidFixture(this,axeHeadShape,5000);
        SolidFixture axeShaftFixture = new SolidFixture(this,axeShaftShape,250);
        addImage(image);
        this.setDurability(defaultDurability);
    }
    {
        axeCounter++;
    }

    //Getters
    //

    public static int getDefaultDurability() {
        return defaultDurability;
    }

    //setters

    public void setDurability(int durability) {
        this.durability = durability;
    }
}

