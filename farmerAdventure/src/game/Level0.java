package game;

import org.jbox2d.common.Vec2;

public class Level0 extends GameLevel {
    @Override
    public void createGrounds() {

    }

    @Override
    public Vec2 housePosition() {
        return new Vec2(0,0);
    }

    @Override
    public boolean isComplete() {
        return true;
    }

    @Override
    public String levelBackground() {
        return "";
    }
}
