package game;

import org.jbox2d.common.Vec2;

public class Level5 extends GameLevel {
    @Override
    public void populate(Game game) {
        super.populate(game);
        for (int i =0; i<11; i++) {
            if(i != 4 && i != 8) {
                Flower flower = new Flower(this);
                flower.setPosition(new Vec2(-8 + 2 * i, -9f));
                flower.addCollisionListener(new ObjectListener(game));
            } else {
                Tree tree = new Tree(this);
                if(i == 4)
                    tree.addBanana();
                tree.setPosition(new Vec2(-8 + 2 * i, -9f));
                tree.addCollisionListener(new ObjectListener(game));
            }
        }
        Tree tree = new Tree(this);
        tree.setPosition(new Vec2(6,-5.5f));
        tree.addBanana();
        tree.addCollisionListener(new ObjectListener(game));
        tree = new Tree(this);
        tree.setPosition(new Vec2(15,-1.5f));
        tree.addBanana();
        tree.addCollisionListener(new ObjectListener(game));
        for(int i=0; i<4;i++){
            Flower flower = new Flower(this);
            flower.setPosition(new Vec2(20 + 2 * i, -5f));
            flower.addCollisionListener(new ObjectListener(game));
        }

            Stone stone = new Stone(this);
            stone.setPosition(new Vec2(3, -5.5f));
            stone.addCollisionListener(new ObjectListener(game));

    }

    @Override
    public void createGrounds() {
        //floor 1 platform
        Platform platform1 = new Platform(this,5,new Vec2(3.5f,-8));
        //main ground by new constructor
        Platform ground = new Platform(this,14,new Vec2(0,-12));
        ground.addWallLeft(10);//very left wall
        ground.addWallRight(2);
        //1nd floor platform
        Platform hill = new Platform(this,10, new Vec2(24,-8));
        hill.addWallRight(8);
    }

    @Override
    public Vec2 housePosition() {
        return new Vec2(-10,-7);
    }

    @Override
    public boolean isComplete() {
        if (Flower.getFlowerCounter() == 0)
            return true;
        return false;
    }

    @Override
    public String levelBackground() {
        return "backgroundBlue";
    }
}
