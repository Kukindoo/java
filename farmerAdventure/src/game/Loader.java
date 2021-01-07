package game;

import org.jbox2d.common.Vec2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Loader extends GameLevel {
    private Game game;
    private int level;
    private  Vec2 housePos;
    private String backgroundImg;
    public void populateIt(Game game) throws IOException{
        this.game = game;
        Game.setLoading(true);
        game.setWorld(this);
        super.populate(game);
        String shortPath = "data" + File.separator + "saves" + File.separator + game.getPlayer().getFarmerName() + ".txt";
        File f = new File(shortPath);
        FileReader fr = null;
        BufferedReader reader = null;
        int lineNo = 0;
        Flower.setFlowerCounter(0);
        try{
            fr = new FileReader(f);
            reader = new BufferedReader(fr);
            String line = reader.readLine();
            // Setting Farmer name
            while(lineNo == 0){
                backgroundImg = line;
                lineNo++;
            }

            while (lineNo == 1){
                line = reader.readLine();
                Game.setLevel(Integer.parseInt(line));
                this.level = Integer.parseInt(line);

                System.out.println(line);
                lineNo++;
            }
            // loading  rest of the file with objects
            while ((line = reader.readLine()) != null){
                System.out.println(line);
                String[] tokens = line.split(",");
                String className = tokens[0];
                float x = Float.parseFloat(tokens[1]), y = Float.parseFloat(tokens[2]);
                Vec2 pos = new Vec2(x,y);
                //Creating all kinds of object in game
                if(className.equals("BackUpFarmer")){
                    // I can do it like that because farmer back up always comes first in file
                    setUpFarmer(tokens,pos);
                    game.setFarmerBackUp(this.getPlayer());
                }
                if(className.equals("Farmer")){
                    setUpFarmer(tokens,pos);
                }

                if(className.equals("House")){
                    housePos = pos;
                }
                if(className.equals("Flower")){
                    Flower flower = new Flower(this);
                    flower.setPosition(pos);
                    flower.addCollisionListener(new ObjectListener(game));
                }
                if(className.equals("Tree")){
                    Tree tree = new Tree(this);
                    tree.setPosition(pos);
                    tree.addCollisionListener(new ObjectListener(game));
                    if (tokens[3].equals("true")){
                        tree.addBanana();
                    }
                }
                if (className.equals("Axe")){
                    Axe axe = new Axe(this);
                    axe.setPosition(pos);
                    axe.addCollisionListener(new ObjectListener(game));
                }
                if (className.equals("Branch")){
                    Branch branch = new Branch(this);
                    branch.setPosition(pos);
                    branch.addCollisionListener(new ObjectListener(game));
                }
                if(className.equals("Stone")){
                    Stone stone = new Stone(this);
                    stone.setPosition(pos);
                    stone.addCollisionListener(new ObjectListener(game));
                }
                if (className.equals("Stump")){
                    Stump stump = new Stump(this);
                    stump.setPosition(pos);
                }
                if(className.equals("Platform")){
                    Platform platform = new Platform(this, Float.parseFloat(tokens[4]),Float.parseFloat(tokens[3]),pos,0);
                    if (tokens[5].equals("truetrue")){
                        if(tokens[6].equals(tokens[7])) {
                            platform.addWalls(Float.parseFloat(tokens[6]));
                        } else {
                            platform.addWallRight(Float.parseFloat(tokens[7]));
                            platform.addWallLeft(Float.parseFloat(tokens[6]));
                        }
                    } else if(tokens[5].equals("falsetrue")){
                        platform.addWallRight(Float.parseFloat(tokens[7]));
                    } else  if(tokens[5].equals("truefalse")){
                        platform.addWallLeft(Float.parseFloat(tokens[6]));
                    }
                }
            }



        } catch(IOException e) {
            System.out.println(e);
        }finally {
            if (reader != null) {
                reader.close();
            }
            if (fr != null) {
                fr.close();
            }
        }

        game.callNewWorld();
        Game.setLoading(false);
    }
    //
    //Getters
    //

    //
    //Methods
    //
    private void setUpFarmer(String[] tokens, Vec2 pos){
        this.getPlayer().setPosition(pos);
        this.getPlayer().setFarmerLevel(Integer.parseInt(tokens[3]));
        this.getPlayer().setActionCount(Integer.parseInt(tokens[4]));
        this.getPlayer().setAxe(Integer.parseInt(tokens[5]));
        this.getPlayer().setStone(Integer.parseInt(tokens[6]));
        //Inventory slot 3
        //Inventory slot 4
        //Inventory slot 5
        //Inventory slot 6
        //Inventory slot 7
        this.getPlayer().setBranch(Integer.parseInt(tokens[12]));
        this.getPlayer().setFibre(Integer.parseInt(tokens[13]));
    }
    //
    //Overrides
    //
    @Override
    public void createGrounds() {

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
        return backgroundImg;
    }
    //
    //Setters
    //
}
