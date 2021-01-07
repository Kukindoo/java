
package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 *
 */
public class Farmer extends Walker {
    private static final Shape farmerRightShape = new PolygonShape(
            -0.591f,-1.102f, 0.561f,-1.098f, 0.53f,0.738f, 0.071f,1.053f, -0.159f,1.044f, -0.595f,0.756f);
    private static final BodyImage imageRight = new BodyImage("data/farmerRight.png", 2.25f);
    private static final Shape farmerLeftShape = new PolygonShape(
            -0.555f,-1.102f, 0.575f,-1.107f, 0.575f,0.743f, 0.129f,1.044f, -0.078f,1.031f, -0.519f,0.743f);
    private static final BodyImage imageLeft = new BodyImage("data/farmerLeft.png", 2.25f);
    private String farmerName = "Will";
    private int farmerLevel; //
    private int actionCount; //collect action counts to get points towards new level
    private static SoundClip powerUpSound;
    //Inventory for axe, fibres and other stuff
    private int[] inventory = new int[10];
    // [0] slot for Axe
    // [1] slot for stone
    // [2]
    // [3]
    // [4]
    // [5]
    // [6]
    // [7] slot for branches
    // [8] slot for fibre
    public Farmer(World world) {
        super(world);
        SolidFixture farmerFixture = new SolidFixture(this, farmerRightShape,150);
        this.addImage(imageRight);
        farmerLevel = 1;
        inventory[0] = 0;
    }
    static{
        try {
            powerUpSound = new SoundClip("data/sounds/Powerup.wav");
        } catch (UnsupportedAudioFileException| IOException| LineUnavailableException e){
            System.out.println(e);
        }
    }

    //
    //Getters
    //
    public int getAxeDurability(){
        return inventory[0];
    }
    public int getActionCount() {
        return actionCount;
    }
    public int getFarmerLevel(){
        return farmerLevel;
    }
    public String getFarmerName() {
        return farmerName;
    }
    public int[] getInventory() {
        return inventory;
    }
    public int getNumberOfStones(){
        return inventory[1];
    }

    //
    //Methods
    //
    public void increaseCount(){
        actionCount++;
        System.out.println("You have collected " + actionCount +" experience so far!");
        increaseLevel();
    }
    public void increaseLevel(){
        if(actionCount == farmerLevel || actionCount > farmerLevel){
            setActionCount(actionCount-farmerLevel);
            setFarmerLevel(farmerLevel+1);
            if(!Game.isFsxMuted()) {
                powerUpSound.setVolume(Game.getGameVolume());
                powerUpSound.play();
            }
            System.out.println("Your farmer level is now "+farmerLevel+"! Congratulation");
        }
    }
    //
    // Setters
    //
    public void setFarmerLevel(int farmerLevel) {
        this.farmerLevel = farmerLevel;

    }

    public void setActionCount(int actionCount) {
        this.actionCount = actionCount;
    }

    //add charges to axe
    public void setAxe() {
        inventory[0] = inventory[0]+ Axe.getDefaultDurability();
        System.out.println("You picked up an axe! Durability of your axe is now: "+inventory[0]);
    }
    public void setAxe(int dur){
        inventory[0] = dur;
    }
    public void useOfAxe(){
        inventory[0]--;
        System.out.println("You used your axe! Durability of your axe is now: "+inventory[0]);
    }
    //Branch collector codes
    public  void  addBranch(){
        inventory[7]++;
    }
    public void setBranch(int f){
        inventory[7] = f;
    }
    public void useBranch(){
        inventory[7]--;
    }
    //fibre collector codes
    public  void  addFibre(){
        inventory[8]++;
    }
    public void setFibre(int f){
        inventory[8] = f;
    }
    public void useFibre(){
        inventory[8]--;
    }
    //
    //add stone to inventory
    public void storeStone(){
        this.inventory[1] = this.inventory[1]+1;
        System.out.println("I have "+inventory[1]+" stone in my pocket.");
    }
    //set stones
    public void setStone(int s){
        inventory[1] = s;
    }
    public void useStone(){
        inventory[1]--;
        System.out.println("I have "+inventory[1]+" stone in my pocket.");
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }

    public void setInventory(int[] inventory) {
        this.inventory = inventory;
    }
}
