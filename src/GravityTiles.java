import java.awt.*;

/**
 * Created by John8 on 3/3/2017.
 */
public class GravityTiles extends Rectangle {
    private int gravDirection;
    public static int numTiles = 0;
    public GravityTiles(int x, int y, int gravDirection){
        setBounds(x,y,40,40);
        this.gravDirection = gravDirection;
        numTiles++;
    }
    public int getGravDirection(){
        return gravDirection;
    }
    public static int getnumTiles(){
        return numTiles;
    }
    public void tick(){}
    public void render(Graphics g){
        g.setColor(Color.CYAN);
        g.fillRect(x,y,width,height);
    }

}
