import java.awt.*;

/**
 * Created by John8 on 2/26/2017.
 */
public class PongBall extends Rectangle{
    private int count = 0,speed = 16;
    public boolean hitSide = true, hitEnd = true;

    public PongBall(int x,int y){

        setBounds(x,y,32,32);
    }
    public void tick(){
        if (count == 1200){
            speed++;
            count = 0;
        }
        if (x >= 608) hitSide = false;
        if (x<= 0)hitSide = true;
        if (y >= 448) hitEnd = false;
        if (y <= 0) hitEnd = true;
        if (hitSide) x+=speed;
        else x-= speed;
        if (hitEnd)y+=speed;
        else y-=speed;
        count++;
    }
    public void render(Graphics g){
        g.setColor(Color.yellow);
        g.fillOval(x,y,width,height);
}}
