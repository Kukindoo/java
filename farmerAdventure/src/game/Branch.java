package game;

import city.cs.engine.*;

public class Branch extends DynamicBody {
    private static int branchCounter;
    private Shape branchShape = new PolygonShape(-0.185f,-0.585f, -0.212f,0.483f, 0.253f,0.385f, 0.203f,-0.03f, 0.108f,-0.585f);
    private BodyImage image = new BodyImage("data/branchDefault.png",1.25f);
    public Branch(World w) {
        super(w);
        SolidFixture branchFixture = new SolidFixture(this,branchShape,250);
        addImage(image);
    }


    //
    //Getters
    //

    public static int getBranchCounter() {
        return branchCounter;
    }

    //
    //Setters
    //


    public static void setBranchCounter(int branchCounter) {
        Branch.branchCounter = branchCounter;
    }
}
