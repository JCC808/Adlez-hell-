import java.awt.*;

/**
 * Created by John8 on 3/5/2017.
 *
 * This is going to contain the x tiles that restart the level and the end tiles which progress you on to the next level
 */
public class LevelTiles extends Rectangle{
//type: true = end tile, false = restart tile
    private boolean type;

//constructor for normal... only needs x,y and whether it is the end or restart
    public LevelTiles(int x, int y, boolean type){
        setBounds(x,y,40,40);
        this.type = type;

    }
//standard render()
    public void render(Graphics g){
        if (type){
            g.setColor(Color.GREEN);
        } else {
            g.setColor(Color.RED);
        }
        g.fillOval(x,y,width,height);
    }
    public boolean getType(){return type;}
}
