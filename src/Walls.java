import java.awt.*;

/**
 * Created by John8 on 2/27/2017.
 */
public class Walls extends Rectangle {
    //compatible with every wall tile entity... should all be objects of this class
    public Walls(int x, int y,int width, int height){
        setBounds(x,y,width,height);
    }
    public void render(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(x,y,width,height);
    }
}
