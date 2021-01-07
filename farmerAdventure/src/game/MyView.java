package game;

import city.cs.engine.BoxShape;
import city.cs.engine.UserView;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MyView extends UserView {
    private Farmer farmer;
    private Game game;
    private boolean transitionProcess;
    private Image background;
    private Image[] axeImage = new Image[2];
    private Image[] stoneImage = new Image[2];
    private Image[] fibreImage = new Image[2];
    private Image[] branchImage = new Image[2];
    private int scrollPos = 0, currentSlot = 1;
    private String farmerPersonalMessage = "";

    private int[] inventory = new int[10];
    public MyView(World w,Game game, int width, int height) {
        super(w, width, height);
        farmer = game.getPlayer();
        this.game = game;
        this.background = new ImageIcon("data/backgroundDefault.jpg").getImage();
        System.out.println("I loaded new image");
    }
    {
        //Axe Images
        axeImage[0] = new ImageIcon("data/axeDisabled.png").getImage();
        axeImage[1] = new ImageIcon("data/axeDefault.png").getImage();
        //Stone Images
        stoneImage[0] = new ImageIcon("data/stoneDisabled.png").getImage();
        stoneImage[1] = new ImageIcon("data/stoneDefault.png").getImage();
        // Fibre image
        fibreImage[0] = new ImageIcon("data/stringDisabled.png").getImage();
        fibreImage[1] = new ImageIcon("data/stringDefault.png").getImage();
        // branches image
        branchImage[0] = new ImageIcon("data/branchDisabled.png").getImage();
        branchImage[1] = new ImageIcon("data/branchInventory.png").getImage();
    }

    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(background,0,0,this);

    }

    @Override
    protected void paintForeground(Graphics2D g) {
        BasicStroke bs10 =new BasicStroke(10);
        BasicStroke bs12 = new BasicStroke(12);
        BasicStroke bs2 = new BasicStroke(2);
        BasicStroke bs5 = new BasicStroke(5);
        //
        //Visualisation of progress bar
        //
        g.setColor(Color.black);
        g.drawString("Farmer level: "+farmer.getFarmerLevel(),15,15);
        g.setStroke(bs12);
        g.setColor(Color.blue);
        g.drawLine(12,25,252,25);
        //dividing of progress bar
        for(int i =0, n = farmer.getFarmerLevel(); i<n; i++){
            int unit = (int) Math.floor(240/farmer.getFarmerLevel());
            if(farmer.getActionCount()-1 < i) {
                g.setColor(Color.red);
            } else {
                g.setColor(Color.green);
            }
            g.setStroke(bs10);
            g.drawLine( 12+i*unit, 25, 12+unit+i*unit, 25);
            if(i>0) {
                g.setStroke(bs2);
                g.setColor(Color.black);
                g.drawLine(10 + unit * i, 20, 10 + unit * i, 30);
            }
        }
        //
        //Level up visual
        //

        //
        //eating banana visual
        //
        g.setColor(Color.yellow);
        Vec2 inGameFarmPos = farmer.getPosition();
        Point2D.Float positionInView = game.getView().worldToView(inGameFarmPos);
        g.drawString(farmerPersonalMessage, positionInView.x, positionInView.y-25);
        //
        //Visualisation of inventory
        //
        g.setStroke(new BasicStroke(36));
        g.setColor(Color.gray);
        g.drawLine(18,70,18,390);
        g.setStroke(bs5);
        g.setColor(Color.darkGray);
        for(int i=0; i<10;i++) {
            int step = 40;
            g.drawLine(0, 50+i*step, 36, 50+i*step);
        }
        g.drawLine(36,50,36,410);

        //
        //Axe in the inventory
        //
        if (farmer.getAxeDurability() == 0) {
            g.drawImage(axeImage[0], -3, 52, 40, 40, this);
        } else {
            g.drawImage(axeImage[1], -3, 52, 40, 40, this);
        }
        g.setColor(Color.yellow);
        g.drawString(""+farmer.getAxeDurability(),25,85);


        //
        //Stone in the inventory
        //
        if (farmer.getNumberOfStones() == 0){
            g.drawImage(stoneImage[0], -3, 87, 40, 40, this);
        } else {
            g.drawImage(stoneImage[1], -3, 87, 40, 40, this);
        }
        g.setColor(Color.yellow);
        g.drawString(""+farmer.getNumberOfStones(),25,125);
        //
        //Branch in the inventory
        //
        if(farmer.getInventory()[7] == 0){
            g.drawImage(branchImage[0],-2,332,36,36,this);
        } else {
            g.drawImage(branchImage[1],-2,332,36,36,this);
        }
        g.drawString(""+farmer.getInventory()[7],25,365);
        //
        //Fibre in the inventory
        //
        if(farmer.getInventory()[8] == 0){
            g.drawImage(fibreImage[0],-3,372,36,36,this);
        } else {
            g.drawImage(fibreImage[1],-3,372,36,36,this);
        }
        g.drawString(""+ farmer.getInventory()[8],25,405);
        //
        //Inventory selector, has to be drawn after inventory
        //
        inventorySelectorBox(g,game.getController().getSelected());
        //
        //Transition between levels part// has to be drawn very last
        //
        if (transitionProcess){
            String yourFarmerLevelText = "You achieved level " + farmer.getFarmerLevel() +" with your farmer in this level!";
            String welcomeToNewLevelText = "Welcome to level " + (Game.getLevel()+1)+"!";
            String messageLine[] = {"","","",""};
            //making a screen black
            makeBlack(g);
            // big writing in midle
            g.setStroke(bs12);
            g.setColor(Color.white);
            g.setFont(new Font("Serif", Font.BOLD,24));
            if(Game.getLevel()<Game.getMaxLevel()) {
                //do not change anything
            } else {
                welcomeToNewLevelText = "Congratulation, You finished a game!";
            }
            g.drawString(welcomeToNewLevelText, getWidth() / 2 - (welcomeToNewLevelText.length() * 5), (getHeight() / 2));

            //small writing under.
            g.setFont(new Font("Serif", Font.BOLD,14));
            if (Game.getLevel()>0)
            g.drawString(yourFarmerLevelText,getWidth()/2 - (yourFarmerLevelText.length()*3),(getHeight()/2)+18);
            if(Game.getLevel() == 0) {
                messageLine[0] = "Try to collect all flowers and get home!";
                messageLine[1] = "Use W A S D to move!";
                messageLine[2] = "GUI is disabled during transition between levels!";
                messageLine[3] = "";

            }
            if(Game.getLevel() == 1) {
                messageLine[0] = "Try to find your way!";
                messageLine[1] = "Use space to JUMP!";
                messageLine[2] = "";
                messageLine[3] = "";
            }
            if(Game.getLevel() == 2) {
                messageLine[0] = "Good job!";
                messageLine[1] = "Use axe to cut down trees!";
                messageLine[2] = "You can see on the left how many charges your axe has.";
                messageLine[3] = "Pick up axe to recharge your axe.";
            }
            if(Game.getLevel() == 3) {
                messageLine[0] = "Use stones to kick down axes you can't reach!";
                messageLine[1] = "To select stone in your inventory press 2!";
                messageLine[2] = "Power is vector-based further your cursor is from character,";
                messageLine[3] = "the stronger throw is. Good luck!";
            }
            if(Game.getLevel() == 4) {
                messageLine[0] = "Use stones to shoot down bananas from tree!";
                messageLine[1] = "Tree with bananas can NOT be chopped down";
                messageLine[2] = "If you need extra axe, you can craft one!";
                messageLine[3] = "Press C to craft axe! You need one branch, string and stone!";
            }

            g.setFont(new Font("Serif", Font.BOLD,14));
            g.drawString(messageLine[0],getWidth()/2 - (messageLine[0].length()*3),(getHeight()/2)+34);
            g.drawString(messageLine[1],getWidth()/2 - (messageLine[1].length()*3),(getHeight()/2)+50);
            g.drawString(messageLine[2],getWidth()/2 - (messageLine[2].length()*3),(getHeight()/2)+66);
            g.drawString(messageLine[3],getWidth()/2 - (messageLine[3].length()*3),(getHeight()/2)+82);
        }
    }

    //
    //Methods
    //
    public void callPersonalMessage(){
        Timer farmerPersonalTimer = new Timer();
        TimerTask emptyPersonalMessage = new TimerTask() {
            @Override
            public void run() {
                setFarmerPersonalMessage("");
            }
        };
        TimerTask bananaPersonalMessage = new TimerTask() {
            @Override
            public void run() {
                setFarmerPersonalMessage("Yum, yum, yum");
            }
        };
        farmerPersonalTimer.schedule(bananaPersonalMessage,0);
        farmerPersonalTimer.schedule(emptyPersonalMessage,2000);

    }
    private void makeBlack(Graphics2D g){
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(getWidth()));
        g.drawRect(0,0,getWidth(),getHeight());
    }
    private void inventorySelectorBox(Graphics2D g, int slot){
        //small red box to select stuff
        if(slot == 0){
            slot = 10;
        }
        g.setColor(Color.red);
        g.setStroke(new BasicStroke(2));
        int boxY = 52 +(40* (slot-1));
        g.drawLine(0,boxY,34,boxY); // top line
        g.drawLine(0,boxY+37,34,boxY+37); // bottom line
        g.drawLine(0,boxY,0,boxY+37); // left line
        g.drawLine(35,boxY,35,boxY+37);
    }
    private void scrollingText(Graphics2D g, String text){ // Need to make it work!
        Timer scroll = new Timer();
        //g.drawString(completedLevelText,getWidth()/2 - (completedLevelText.length()*3),(getHeight()/2));
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                scrollPos = scrollPos+5;
            }
        };

    }
    //
    //Setters
    //
    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }
    public void setBackground(String imageAddress){
        this.background = new ImageIcon("data/"+imageAddress+".png").getImage();
        System.out.println("I loaded new picture");
    }

    public void setFarmerPersonalMessage(String farmerPersonalMessage) {
        this.farmerPersonalMessage = farmerPersonalMessage;
    }

    public void setTransitionProcess(boolean transitionProcess) {
        this.transitionProcess = transitionProcess;
    }

    public void setCurrentSlot(int currentSlot) {
        this.currentSlot = currentSlot;
    }
}
