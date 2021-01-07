package game;

import org.jbox2d.common.Vec2;

public class Level1 extends GameLevel {
    @Override
    public void populate(Game game) {
        super.populate(game);

        for (int i =0; i<11; i++) {
            Flower flower = new Flower(this);
            flower.setPosition(new Vec2(-7f + 2 * i, -9f));
            flower.addCollisionListener(new ObjectListener(game));
        }
    }

    @Override
    public void createGrounds() {
        //main ground by new constructor
        Platform ground = new Platform(this,14,new Vec2(0,-12));
        ground.addWalls(10);
    }

    @Override
    public Vec2 housePosition() {
        return new Vec2(-10f,-7f);
    }

    @Override
    public boolean isComplete() {
        if (Flower.getFlowerCounter() == 0)
            return true;
        return false;
    }

    @Override
    public String levelBackground() {
        return "backgroundDefault";
    }
}
