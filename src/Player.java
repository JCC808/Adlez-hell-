import java.awt.*;

/**
 * Created by John8 on 2/24/2017.
 */
public class Player extends Rectangle {

//Love you guys and all but im only gonna comment this method because the other
//movie things are basically the same

//these are the letters, they store boolean of pressed or not (see Game: KeyPressed())
    public boolean w=false,a=false,s=false,d=false;
//this changes how much the obj moves when it is being told to move, motion/tick
    private int speed = 4;
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
    public void tick(Walls[][] grid,int level){
//this crazy looking, really hard to read for loop is the hit boxes for the blocks
        for (int i=0; i<192; i++){
            if (grid[level][i].getWidth()>0){
                if((this.getMaxY() >= grid[level][i].getMinY()) && (this.getMaxY() < grid[level][i].getMinY()+4)&&
                   (this.getMaxX() >= grid[level][i].getMinX()) && (this.getMinX() <= grid[level][i].getMaxX())) s = false;
                if((this.getMinY() >= grid[level][i].getMaxY()) && (this.getMinY() < grid[level][i].getMaxY()+4)&&
                   (this.getMaxX() >= grid[level][i].getMinX()) && (this.getMinX() <= grid[level][i].getMaxX())) w = false;
                if((this.getMaxX() >= grid[level][i].getMinX()) && (this.getMaxX() < grid[level][i].getMinX()+4)&&
                   (this.getMaxY() >= grid[level][i].getMinY()) && (this.getMinY() <= grid[level][i].getMaxY())) d = false;
                if((this.getMinX() >= grid[level][i].getMaxX()) && (this.getMinX() < grid[level][i].getMaxX()+4)&&
                   (this.getMaxY() >= grid[level][i].getMinY()) && (this.getMinY() <= grid[level][i].getMaxY())) a = false;
            }
        }

        if(a)x-=speed;
        if(d)x+=speed;
        if(w)y-=speed;
        if(s)y+=speed;
        xone=x;
        yone=y;
    }
//sets the part of g to be used in the buffered strategy in Game
    public void render(Graphics g){
        g.setColor(Color.yellow);
        g.fillRect(x,y,width,height);
    }
}
