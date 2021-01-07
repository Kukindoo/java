package game;

import city.cs.engine.World;
import org.jbox2d.common.Vec2;

public abstract class GameLevel extends World {
    private Farmer farmer;
    private House house;
    private static int restarts;
    private Flower[] flowers;
    private Tree[] trees;
    private Star[] stars;
    private Axe[] axes;


    public void populate(Game game){
        setGravity(15);
        createGrounds();
        farmer = new Farmer(this);
        farmer.setPosition(housePosition());
        house = new House(this);
        house.setPosition(housePosition());
        house.removeAllCollisionListeners();
        house.addCollisionListener(new ObjectListener(game));

    }









    //
    ///Getters
    //
    public Farmer getPlayer(){
        return farmer;
    }

    //
    //Setters
    //




    //
    //abstract classes
    //
    public  abstract Vec2 housePosition();
    public  abstract  void createGrounds();
    public abstract boolean isComplete();
    public abstract String levelBackground();
}
