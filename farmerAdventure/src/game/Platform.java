package game;

import city.cs.engine.*;
import city.cs.engine.Shape;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class Platform extends StaticBody{
    private Shape platformShape;
    private SolidFixture platformFixtute;
    private  Body platformBody;
    private World world;
    private float halfWidth = 0.2f;
    private float halfHeight = 0.2f;
    private Color colour = Color.getHSBColor(100/255f,1f,0.75f); //green colour
    private Vec2 position = new Vec2(0,0);
    private float angle = 0;
    private boolean wallLeft, wallRight;
    private float heightWallLeft = 0, heightWallRight = 0;

    public Platform(World w, float halfWidth, float halfHeight, Vec2 position, float angle) {
        super(w);
        this.world = w;
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
        this.position = position;
        this.angle = angle;
    /*}

    // Constructor with angel and bellow
    Platform(World world, float halfWidth, float posX, float posY, float angle){*/
        this.position.x = position.x;
        this.position.y = position.y;
        platformShape = new BoxShape( halfWidth, halfHeight, new Vec2(0,0));
        platformBody = new StaticBody(world, platformShape);
        platformFixtute = new SolidFixture(platformBody, platformShape,500);
        platformBody.setAngleDegrees(angle);
        platformBody.setPosition(new Vec2(position.x,position.y));
        platformBody.setFillColor(colour);
        platformBody.setLineColor(colour);
    }
    //Constructor with position and bellow
    Platform(World world, float halfWidth, Vec2 position){

        this(world,halfWidth,0.2f,position,0);
    }
    //Constructor with custom width
    Platform(World world, float halfWidth){
    this(world,halfWidth,new Vec2(0,0));
    }
    // Plain constructor
    Platform(World world){
        this(world, 0.2f);
    }
    //
    //Methods to add walls
    //
    public void addWallRight( float heightOfWall){
        heightWallRight = heightOfWall;
        Shape newWallShape = new BoxShape(this.halfHeight,heightOfWall);
        Body newWallBody = new StaticBody(this.world, newWallShape);
        //SolidFixture newWall = new SolidFixture(newWallBody,newWallShape);
        //newWallBody.setAngleDegrees(90);
        newWallBody.setPosition(new Vec2(this.position.x+this.halfWidth+this.halfHeight,this.position.y+heightOfWall-this.halfHeight));
        newWallBody.setFillColor(colour);
        newWallBody.setLineColor(colour);
        wallRight = true;
    }
    public void addWallLeft (float heightOfWall){
        heightWallLeft = heightOfWall;
        Shape newWallShape = new BoxShape(this.halfHeight,heightOfWall);
        Body newWallBody = new StaticBody(this.world, newWallShape);
        //SolidFixture newWall = new SolidFixture(this.platformBody,newWallShape);
        newWallBody.setPosition(new Vec2(this.position.x-this.halfWidth-this.halfHeight,this.position.y+heightOfWall-this.halfHeight));
        newWallBody.setFillColor(colour);
        newWallBody.setLineColor(colour);
        wallLeft= true;
    }
    public void addWalls (float heightOfWall){
        addWallLeft(heightOfWall);
        addWallRight(heightOfWall);
    }
    //
    //setters
    //

    public void setHalfWidth(float halfWidth) {
        if (halfWidth >=0) { //Making sure there is a dimension
            this.halfWidth = halfWidth;
        } else {
            EpossitiveNumber();
        }
    }

    public void setHalfHeight(float halfHeight) {
        if(halfHeight >= 0) { //Making sure there is a dimension
            this.halfHeight = halfHeight;
        } else {
            EpossitiveNumber();
        }
    }
    public void setPosition(Vec2 position) {
        this.position = position;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    //
    //Getters
    //

    public float getHalfWidth() {
        return halfWidth;
    }

    public float getHalfHeight() {
        return halfHeight;
    }

    public Vec2 getPosition() {
        return position;
    }

    public float getAngle() {
        return angle;
    }

    public float getHeightWallLeft() {
        return heightWallLeft;
    }

    public float getHeightWallRight() {
        return heightWallRight;
    }

    public Body getPlatformBody() {
        return platformBody;
    }

    public boolean isWallLeft() {
        return wallLeft;
    }

    public boolean isWallRight() {
        return wallRight;
    }

    //
    // Error messages
    //
    private void EpossitiveNumber(){
        System.out.println("Enter positive number");
    }
}
