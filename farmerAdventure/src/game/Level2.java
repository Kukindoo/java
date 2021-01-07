package game;

import org.jbox2d.common.Vec2;

public class Level2 extends GameLevel{

    @Override
    public void populate(Game game) {
        super.populate(game);



        for (int i =0; i<11; i++) {
            if(i != 4) {
                Flower flower = new Flower(this);
                flower.setPosition(new Vec2(-7f + 2 * i, -9f));
                flower.addCollisionListener(new ObjectListener(game));
            } else {
                Tree tree = new Tree(this);
                tree.setPosition(new Vec2(-8 + 2 * i, -9f));
                tree.addCollisionListener(new ObjectListener(game));
        }

        }
        Tree tree = new Tree(this);
        tree.setPosition(new Vec2(3,-5.5f));
        tree.addCollisionListener(new ObjectListener(game));
        Stone stone = new Stone(this);
        stone.setPosition(new Vec2(-6,-8));
        stone.addCollisionListener(new ObjectListener(game));
    }

    @Override
    public void createGrounds() {
        //first floor
        Platform platform1 = new Platform(this,5,new Vec2(3,-8));
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
