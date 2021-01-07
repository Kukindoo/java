package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import org.jbox2d.common.Vec2;

import java.util.Random;

public class ObjectListener implements CollisionListener {
    private Game game;
    private MyView view;
    public ObjectListener(Game game){
        this.game = game;
        this.view = game.getView();

    }
    @Override
    public void collide(CollisionEvent eventE) {
        Farmer farmer = game.getPlayer();
        //
        //Axe collision
        //
        if(eventE.getOtherBody() == farmer
                && eventE.getReportingBody() instanceof Axe){
            Axe axe = (Axe) eventE.getReportingBody();
            axe.destroy();
            farmer.setAxe();
        }
        //
        //Banana collision
        //
        if(eventE.getReportingBody() instanceof Banana
                && eventE.getOtherBody() == farmer){
            System.out.println("I touched banana");
            eventE.getReportingBody().destroy();
            game.getView().callPersonalMessage();
            for (int j = 0 ; j <3 ; j++)
            farmer.increaseCount();
        }
        //
        //Branch collision
        //
        if (eventE.getOtherBody() == farmer
                && eventE.getReportingBody() instanceof Branch){
            Branch branch = (Branch) eventE.getReportingBody();
            branch.destroy();
            farmer.increaseCount();
            Branch.setBranchCounter(Branch.getBranchCounter()-1);
            farmer.addBranch();
        }
        //
        //Flower Collision
        //
        if(eventE.getOtherBody() == farmer
                && eventE.getReportingBody() instanceof Flower){
            //code bellow
            Flower flower = (Flower) eventE.getReportingBody();
            flower.destroy();;
            farmer.increaseCount();
            Flower.setFlowerCounter(Flower.getFlowerCounter()-1);
            System.out.println("There is: " +Flower.getFlowerCounter()+" flowers left");

            if( new Random().nextInt(10)>7){
                System.out.println("Vuii, I found an old string in pot!");
                farmer.addFibre();
            }
        }

        //
        //House Collision
        //
        if(eventE.getReportingBody() instanceof House
                && game.isCurrentLevelCompleted()
                && eventE.getOtherBody() == farmer
                && !Game.isTransitionInProgress()){
            //Code bellow
            System.out.println("We are going to next level");
            game.transitionToNextLevel();
        }


        //
        //Tree collision  (with Farmer)
        //
        if(eventE.getOtherBody() == farmer
                && eventE.getReportingBody() instanceof Tree
                && farmer.getFarmerLevel() >=6
                && farmer.getAxeDurability()>0
                && game.getController().getSelected() == 1
                && !((Tree) eventE.getReportingBody()).isBananaPresence()){
            //Code bellow
            Tree tree = (Tree) eventE.getReportingBody();
            Vec2 stumpPos = tree.getPosition();

            tree.destroy();
            for (int i = 0; i<4; i++){
                int direction = 1;
                if (Math.random()<0.5)
                    direction = -1;
                Branch branch= new Branch(game.getPlayer().getWorld());
                branch.setPosition(stumpPos);
                branch.addCollisionListener(new ObjectListener(game));
                branch.setLinearVelocity(new Vec2((float) Math.random()*3*direction,(float)Math.random()*5));
            }
            farmer.useOfAxe();
            Tree.setTreeCounter(Tree.getTreeCounter()-1);
            Stump stump = new Stump(game.getPlayer().getWorld());
            stump.setPosition(stumpPos);
        }
        //
        //Tree Collision (With stone)
        //
        if (eventE.getOtherBody() instanceof Stone){
            if(!Game.isFsxMuted())
            Stone.getStoneSound().play();
        }
        if( eventE.getOtherBody() instanceof Stone
                && eventE.getReportingBody() instanceof Tree
            ){
            System.out.println("Stone hit tree");
            //Checking for banana on tree
            if (((Tree) eventE.getReportingBody()).isBananaPresence()){
                ((Tree) eventE.getReportingBody()).removeBanana();
                Banana banana = new Banana(game.getPlayer().getWorld());
                banana.addCollisionListener(this);
                banana.setPosition(new Vec2(eventE.getReportingBody().getPosition().x,eventE.getReportingBody().getPosition().y+4));
                banana.setLinearVelocity(new Vec2(banana.getPosition().x-game.getPlayer().getPosition().x,banana.getPosition().y-game.getPlayer().getPosition().y));
            }
        }

        //
        //Stone collision
        //
        if (eventE.getOtherBody() == farmer &&
                eventE.getReportingBody() instanceof Stone){
            //Code bellow
            Stone stone = (Stone) eventE.getReportingBody();
            stone.destroy();
            farmer.storeStone();
            Stone.setStoneCounter(Stone.getStoneCounter()-1);
        }

    }
    //
    //Getters
    //

    //
    //Setters
    //


    public void setGame(Game game) {
        this.game = game;
    }
}
