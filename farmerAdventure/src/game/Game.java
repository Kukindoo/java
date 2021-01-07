/*
* Hello to my humble game.
* Classes are organised alphabetically and you find following in order:
* Constructor - Getters - Methods - Setter
* Inside of above all content is again alphabetical unless flow of program don't allow that
*
* For some reason you have to click empty space in left-bottom corner after using GUI.
* */
package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private GameLevel world;
    private static int level, maxLevel =5;
    private static boolean transitionInProgress;
    private Controller controller;
    private MyView view;
    private final JFrame frame = new JFrame("Farmer's adventure");
    private Farmer farmerBackUp;
    private CharacterTracking track;
    private ClickHandler mouse;
    private SoundClip gameMusic;
    private static boolean muted, paused, fsxMuted, loading;
    private static float gameVolume = 0.4f;
    private ControlPanel controlPanel;
    private JFrame debugView;

    public Game(){
        //First initialisation of Game screen setup
        //This one creates empty world to make 1st transition available
        level = 0;
        world = new Level0();
        world.populate(this);
        view = new MyView(world,this, 500,500);
        //Setting first controls
        mouse = new ClickHandler(this);
        controller = new Controller(this);
        track = new CharacterTracking(view,world.getPlayer());
        debugView = new DebugViewer(world, 500, 500);
        view.addMouseListener(mouse);

        //Adding music into game
        try{
            gameMusic = new SoundClip("data/sounds/backgroundMusic.wav");
            gameMusic.loop();
            gameMusic.setVolume(gameVolume);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e){
            System.out.println("Music file error.");
        }
        //First transition
        //Flower has to be one to ensure that the isComplete() method don't send us
        // into bad level before game 1st pauses
        Flower.setFlowerCounter(1);
        transitionToNextLevel();
        Flower.setFlowerCounter(Flower.getFlowerCounter()-1);
        //Control panel adding
        controlPanel = new ControlPanel(this);
        // quit the application when the game window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        // display the world in the window
        frame.add(view);
        //Keyboard input
        frame.addKeyListener(controller);
        //display bottom panel
        frame.add(controlPanel,BorderLayout.PAGE_END);
        // don't let the game window be resized
        frame.setResizable(false);
        // size the game window to fit the world view
        frame.pack();
        // make the window visible
        frame.setVisible(true);
        frame.addMouseListener(new GiveFocus(frame));
        controlPanel.addMouseListener(new GiveFocus(frame));
        world.addStepListener(track);

        // start!
        world.start();
    }

    public static void main(String[] args) {
        new Game();

    }

    //
    //Getters
    //


    public Controller getController() {
        return controller;
    }
    public JFrame getFrame() {
        return frame;
    }
    public static float getGameVolume() {
        return gameVolume;
    }
    public static int getLevel() {
        return level;
    }
    public static int getMaxLevel() {
        return maxLevel;
    }
    public Farmer getPlayer(){
        return world.getPlayer();
    }
    public MyView getView() {
        return view;
    }
    public static boolean isFsxMuted() {
        return fsxMuted;
    }
    public boolean isMuted() {
        return muted;
    }
    public boolean isPaused() {
        return paused;
    }
    public static boolean isTransitionInProgress() {
        return transitionInProgress;
    }

    public Farmer getFarmerBackUp() {
        return farmerBackUp;
    }

    //
    //Methods
    //
    public void callNewWorld(){
        System.out.println("I am calling a new world");
        if(!loading) {
            world.populate(this);
        }
        if(loading){

        }
        controller.setBody(world.getPlayer());
        view.setFarmer(world.getPlayer());
        track.setBody(world.getPlayer());
        world.addStepListener(track);
        view.setWorld(world);
        view.setBackground(world.levelBackground());
        mouse.setFarmer(world.getPlayer());
        //Load player
        if(level > 1 && !loading) {
            //world.getPlayer().setStone(farmerBackUp.getInventory()[0]);
            world.getPlayer().setAxe(farmerBackUp.getInventory()[0]);
            world.getPlayer().setStone(farmerBackUp.getInventory()[1]);
            world.getPlayer().setBranch(farmerBackUp.getInventory()[7]);
            world.getPlayer().setFibre(farmerBackUp.getInventory()[8]);
            world.getPlayer().setFarmerLevel(farmerBackUp.getFarmerLevel());
            world.getPlayer().setActionCount(farmerBackUp.getActionCount());
        }
        world.start();
        transitionInProgress = false;
    }
    public void goToNextLevel(){
        farmerBackUp = world.getPlayer();

        if (level == maxLevel){
            System.exit(0);
        } else{
            level++;
            world = levelSwitch(level);
            //setting up a new world
            callNewWorld();
            debugView.dispose();
            debugView = new DebugViewer(world, 500, 500);
        }
    }
    public boolean isCurrentLevelCompleted(){
        return world.isComplete();
    }

    public GameLevel levelSwitch(int level){
        switch(level){
            case 1:
                return  new Level1();
            case 2:
                return  new Level2();
            case 3:
                return new Level3();
            case 4:
                return new Level4();
            case 5:
                return new Level5();
        }
        return new Level1();
    }

    public void restartLevel(){
        world = levelSwitch(level);
        callNewWorld();
    }
    public void saveGame(GameLevel gameWorld) throws IOException {
        System.out.println("Saving!");
        String inventoryString = "";
        //String path = "D:" + File.separator + "AAA_school" + File.separator + "IN1007 JAVA" + File.separator + "farmerAdventure" + File.separator + "data" + File.separator + "saves" + File.separator + world.getPlayer().getFarmerName() + ".txt";
        String shortPath = "data" + File.separator + "saves" + File.separator + world.getPlayer().getFarmerName() + ".txt";
        FileWriter writer = null;
        File f = new File(shortPath);
        try {
            writer = new FileWriter(f);
            writer.write(""+world.levelBackground()+"\n");
            writer.write(level+"\n");
            for(int i =0, n =  farmerBackUp.getInventory().length; i<n;i++){
                inventoryString = inventoryString +  farmerBackUp.getInventory()[i]+",";
            }
            writer.write("BackUpFarmer" + "," + farmerBackUp.getPosition().x + "," + farmerBackUp.getPosition().y +","+farmerBackUp.getFarmerLevel()+","+farmerBackUp.getActionCount()+","+ inventoryString+ "\n");


            for(DynamicBody body : gameWorld.getDynamicBodies())
                if(body instanceof Farmer){
                    inventoryString = "";
                    for(int i =0, n = ((Farmer) body).getInventory().length; i<n;i++){

                        inventoryString = inventoryString + ((Farmer) body).getInventory()[i]+",";
                    }
                    writer.write(body.getClass().getSimpleName() + "," + body.getPosition().x + "," + body.getPosition().y +","+((Farmer) body).getFarmerLevel()+","+((Farmer) body).getActionCount()+","+ inventoryString+ "\n");

                } else if(body instanceof Tree ){
                    writer.write(body.getClass().getSimpleName() + "," + body.getPosition().x + "," + body.getPosition().y +","+((Tree) body).isBananaPresence() + "\n");

                } else {
                    writer.write(body.getClass().getSimpleName() + "," + body.getPosition().x + "," + body.getPosition().y + "\n");
                }

            for(StaticBody body : gameWorld.getStaticBodies())
                if(body instanceof Platform) {
                    writer.write(body.getClass().getSimpleName() + "," + body.getPosition().x + "," + body.getPosition().y +","+
                            ((Platform) body).getHalfHeight() +","+ ((Platform) body).getHalfWidth()+","+((Platform) body).isWallLeft()+((Platform) body).isWallRight()+","+((Platform) body).getHeightWallLeft()+","+((Platform) body).getHeightWallRight()+"\n");
                } else {
                    writer.write(body.getClass().getSimpleName() + "," + body.getPosition().x + "," + body.getPosition().y + "\n");
                }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
    //visulation between levels
    public  void transitionToNextLevel(){
        transitionInProgress = true;
        Timer transitionTimer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                view.setTransitionProcess(false);
                goToNextLevel();
            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                world.stop();
                view.setTransitionProcess(true);

            }
        };
        transitionTimer.schedule(task2,0);
        transitionTimer.schedule(task,10000); //default value: 10000
    }
    //
    //Setters
    //

    public void setFarmerBackUp(Farmer farmerBackUp) {
        this.farmerBackUp = farmerBackUp;
    }

    public void setFsxMuted(boolean fsxMuted) {
        this.fsxMuted = fsxMuted;
    }
    public void setGameVolume(float gameVolume) {
        this.gameVolume = gameVolume;
    }

    public static void setLevel(int level) {
        Game.level = level;
    }

    public static void setLoading(boolean loading) {
        Game.loading = loading;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    public void setVolume(float volume){
        this.gameMusic.setVolume(volume);
    }

    public void setWorld(GameLevel world) {
        this.world = world;
    }
}
