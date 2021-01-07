package game;

import org.jbox2d.common.Vec2;

import java.util.Timer;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TimerTask;

public class ClickHandler implements MouseListener {
    private Farmer farmer;
    private Game game;
    private Timer rockTimer;
    public ClickHandler(Game game){
        this.game = game;
        farmer = game.getPlayer();
    }
    @Override
    public void mouseClicked(MouseEvent e) {

        Vec2 inGameClickPoint = new Vec2(game.getView().viewToWorld(e.getPoint()));
        Vec2 shootVector = new Vec2(inGameClickPoint.x - farmer.getPosition().x,inGameClickPoint.y - farmer.getPosition().y);
        System.out.println(""+shootVector);
        shootVector.x = shootVector.x * 2;
        shootVector.y = shootVector.y *2;
        if (farmer.getNumberOfStones()>0 && game.getController().getSelected() == 2){
            farmer.useStone();
            Stone stone = new Stone(game.getView().getWorld());
            stone.addCollisionListener(new ObjectListener(game));
            if(farmer.getPosition().x < inGameClickPoint.x){
                stone.setPosition(new Vec2(farmer.getPosition().x+1,farmer.getPosition().y+0.5f));
            } else {
                stone.setPosition(new Vec2(farmer.getPosition().x-1,farmer.getPosition().y+0.5f));
            }
            stone.setLinearVelocity(shootVector);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    //
    //Getters
    //

    //
    //Methods
    //

    //
    //Setters
    //

    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }
}
