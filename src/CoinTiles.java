import java.awt.*;

/**
 * Created by leahbianchi on 3/9/17.
 * creates each coin, it's the same setup as GravityTiles
 */
public class CoinTiles extends Rectangle {
    private static int numCoins; //keeps track of total coins made

    public CoinTiles(int x, int y) {
        setBounds(x, y,40, 40);
        numCoins++;
    }
    public void render(Graphics g){
        g.setColor(Color.green);
        g.fillRect(x,y,width,height);
    }

    //returns numCoins
    public static int getNumCoins(){
        return numCoins;
    }


}
