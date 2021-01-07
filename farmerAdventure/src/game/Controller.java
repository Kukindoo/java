package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Key handler to control an Walker.
 */
public class Controller extends KeyAdapter {
    private static final float JUMPING_SPEED = 12;
    private static final float WALKING_SPEED = 4;
    private static boolean jumpActive = false;
    private Farmer body;
    private Game game;
    private int selected = 1;
    private static boolean facingDirection = true;
    public Controller(Game game) {
        this.game = game;
        body = game.getPlayer();
    }

    /**
     * Handle key press events for walking and jumping.
     * @param e description of the key event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (Game.isTransitionInProgress()) {

        } else {
            int code = e.getKeyCode();
        if (code == KeyEvent.VK_ESCAPE) { // Q = quit
            System.exit(0);
        } else if (code == KeyEvent.VK_SPACE) { // J = jump
            Vec2 v = body.getLinearVelocity();
            // only jump if body is not already jumping
            if (Math.abs(v.y) < 0.01f) {
                body.jump(JUMPING_SPEED);
            }
        } else if (code == KeyEvent.VK_A) {
            body.startWalking(-WALKING_SPEED); // 1 = walk left
            body.removeAllImages();
            ;
            body.addImage(new BodyImage("data/FarmerLeft.png", 2.25f));
            facingDirection = false;
        } else if (code == KeyEvent.VK_D) {
            body.startWalking(WALKING_SPEED); // 2 = walk right
            body.removeAllImages();
            ;
            body.addImage(new BodyImage("data/FarmerRight.png", 2.25f));
            facingDirection = true;
        } else if (code == KeyEvent.VK_1) {
            selected = 1;
        }else if (code == KeyEvent.VK_2) {
            selected = 2;
        } else if (code == KeyEvent.VK_3) {
            selected = 3;
        }else if (code == KeyEvent.VK_4) {
            selected = 4;
        }else if (code == KeyEvent.VK_5) {
            selected = 5;
        }else if (code == KeyEvent.VK_6) {
            selected = 6;
        }else if (code == KeyEvent.VK_7) {
            selected = 7;
        }else if (code == KeyEvent.VK_8) {
            selected = 8;
        }else if (code == KeyEvent.VK_9) {
            selected = 9;
        }else if (code == KeyEvent.VK_0) {
            //This one is not needed yet
            //selected = 0;
        }else if (code == KeyEvent.VK_T) {
            body.setLinearVelocity(new Vec2(0, 5));
        } else if (code == KeyEvent.VK_C){
            if (body.getInventory()[1]>0 && body.getInventory()[7]>0 && body.getInventory()[8] >0) {
                body.useBranch();
                body.useFibre();
                body.useStone();
                body.setAxe();
            }
        } else if(code == KeyEvent.VK_P){
            System.out.println("Farmer level: " + body.getFarmerLevel());
            System.out.println("BAck up Level: " + game.getFarmerBackUp().getFarmerLevel());
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("");

        }
    }
    }

    /**
     * Handle key release events (stop walking).
     * @param e description of the key event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_A) {
            body.setLinearVelocity(new Vec2(-0.6f,body.getLinearVelocity().y));
            body.stopWalking();
        } else if (code == KeyEvent.VK_D) {
            body.setLinearVelocity(new Vec2(0.6f,body.getLinearVelocity().y));
            body.stopWalking();
        }
    }
    public void setBody(Farmer body){
        this.body = body;
    }


    //
    //Getters
    //


    public int getSelected() {
        return selected;
    }

    public static boolean facingDirection() {
        return facingDirection;
    }
    //
    //Methods
    //

    //
    //Setters
    //
}
