import java.awt.*;

/**
 * Created by John8 on 2/24/2017.
 */
public class Player extends Rectangle {

//Love you guys and all but im only gonna comment this method because the other
//movie things are basically the same

//these are the letters, they store boolean of pressed or not (see Game: KeyPressed())
//up down left right are used for checking whether or not it is possible to move in that direction
    public boolean w=false,a=false,s=false,d=false,up=true,down=true,left=true,right=true;
//this changes how much the obj moves when it is being told to move, motion/tick
/*
 *for gravDirection :
    * 0 = no gravity
    * 1 = right
    * 2 = down
    * 3 = left
    * 4 = up
 */
    private int speed = 1, gravity = 1, gravDirection = 0, dx = 0, dy = 0;
//used to see the location of the box, troubleshooting helpout
    public int xone, yone;

//location x,y and the size... thats it
//also it took me a bit to figure this out but extends holds data so int x, y is stored there
    public Player(int x,int y){
        setBounds(x,y,16,16);
        xone=x;
        yone=y;
    }
//if any of the keys are pressed move speed in that direction
//and yes y increases as you go down
    public void tick(Walls[][] grid, GravityTiles[] gravs, int level){
//this crazy looking, really hard to read for loop is the hit boxes for the blocks
        for (int i=0; i<192; i++){
            if (grid[level][i].getWidth()>0){
//I know it looks messy but it works so leave it alone, or ask before changing this
//this checks every one of the 192 possible boxes on all sides.
// then tells the rest of the method which direction it cant move in
                if((this.getMaxY() >= grid[level][i].getMinY()) && (this.getMaxY() < grid[level][i].getMinY()+4)&&
                   (this.getMaxX() > grid[level][i].getMinX()) && (this.getMinX() < grid[level][i].getMaxX())) down = false;
                if((this.getMinY() >= grid[level][i].getMaxY()) && (this.getMinY() < grid[level][i].getMaxY()+1)&&
                   (this.getMaxX() > grid[level][i].getMinX()) && (this.getMinX() < grid[level][i].getMaxX())) up = false;
                if((this.getMaxX() >= grid[level][i].getMinX()) && (this.getMaxX() < grid[level][i].getMinX()+4)&&
                   (this.getMaxY() > grid[level][i].getMinY()) && (this.getMinY() < grid[level][i].getMaxY())) right = false;
                if((this.getMinX() >= grid[level][i].getMaxX()) && (this.getMinX() < grid[level][i].getMaxX()+1)&&
                   (this.getMaxY() > grid[level][i].getMinY()) && (this.getMinY() < grid[level][i].getMaxY())) left = false;
            }
        }
//this checks the edges
        if (x >= 624) right = false;
        if (x<= 0) left = false;
        if (y >= 464) down = false;
        if (y <= 0) up = false;

        for (int i=0;i<gravs[0].getnumTiles();i++)
            if (this.intersects(gravs[i])) gravDirection = gravs[i].getGravDirection();

//normal change in location based off of key depression
// this also doesn't allow you do speed up or slow down your descent caused by gravity
        if(gravDirection == 1){
            d = false;
            a = false;
            dx += gravity;
        }
        if(gravDirection == 2){
            w = false;
            s = false;
            dy+=gravity;
        }
        if(gravDirection == 3){
            d = false;
            a = false;
            dx-=gravity;
        }
        if(gravDirection == 4){
            s = false;
            w = false;
            dy-=gravity;
        }
        if(a)dx-=speed;
        if(d)dx+=speed;
        if(w)dy-=speed;
        if(s)dy+=speed;

//it it cant move in a direction but it wants to... this says no...
        if (!right && dx>0) dx = 0;
        if (!left && dx<0) dx = 0;
        if (!down && dy>0) dy = 0;
        if (!up && dy<0) dy = 0;
//the actual change in (x,y)
        x+=dx;
        y+=dy;
//resets all of the values, ready for another method call.
//I know i could define them just for the method, but i didn't...
//change it if it bothers you that much
        up = true;
        down = true;
        left = true;
        right = true;
        dy=0;
        dx=0;
//leave these, they are for debugging
        xone=x;
        yone=y;
    }
//sets the part of g to be used in the buffered strategy in Game
    public void render(Graphics g){
        g.setColor(Color.yellow);
        g.fillRect(x,y,width,height);
    }
}
