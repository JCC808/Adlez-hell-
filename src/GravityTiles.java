import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;


/**
 * Created by John8 on 3/3/2017.
 */

public class GravityTiles extends Rectangle {
    private int gravDirection;
    public static int numTiles = 0;
    BufferedImage img;
    public GravityTiles(int x, int y, int gravDirection){
        setBounds(x,y,40,40);
        this.gravDirection = gravDirection;
        numTiles++;
        try{
            img = ImageIO.read(new File("resources/ArrowDown.png"));
        }catch (IOException e){
        }
    }
    public int getGravDirection(){
        return gravDirection;
    }
    public static int getnumTiles(){
        return numTiles;
    }
    public void tick(){}
    public void render(Graphics g){
        g.drawImage(img,x,y,null);
    }

}
