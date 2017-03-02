import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.*;

/**
 * Created by John8 on 2/24/2017.
 */
public class Game extends Canvas implements Runnable, KeyListener {


    private boolean isRunning = false;
    private static final int WIDTH = 640, HEIGHT=480;
    private static final String TITLE = "Adlez";
    private static int level = 0;
//main calls a new game starting it out, also starts up jFrame (the paintable window)
    public static void main(String[] args)throws IOException {
        Game game = new Game();
/*
 *creates JFrame (editable window)
 * Frame uses the dimensions of a Game obj (.add)
 * we don't need the resizable to be on. so its set false... always the same window
 * (.pack) finalizes the frame allowing the setting of operations
 * close is when you press the x
 * visible makes it appear
 *
 * game.start obviously starts the game which then uses a thread to keep itself going
 */
        JFrame frame = new JFrame();
        frame.setTitle(Game.TITLE);
        frame.add(game);
        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.start();

    }
//this thread
    private Thread thread;

    public static Player player;
    public static PongBall pongBall;
    //public static Walls[] walls= new Walls[20];
    public static Walls[][] grid = new Walls[7][192];
//initializes the game obj.. this has to have dimensions in it and every object, block, player whatever
// also starts the KeyListener
    public Game()throws IOException{
        Dimension dimension = new Dimension(Game.WIDTH,Game.HEIGHT);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);

        addKeyListener(this);
        player = new Player(Game.WIDTH/2, Game.HEIGHT/2);
        pongBall = new PongBall(Game.WIDTH/2, Game.HEIGHT/2);
//new shit
        int levelsMade = 1;
        int[][] frame = new int[7][192];
        frame[0] = read("resources\\Level0.txt");
        int locx = 0,locy=0;
        for (int i= 0; i<levelsMade; i++) {
            for (int j = 0; j < 192; j++) {
                if (frame[i][j] == 1) grid[i][j] = new Walls(locx * 40, locy * 40, 40, 40);
                if (frame[i][j] == 0) grid[i][j] = new Walls(locx * 40, locy * 40, 0, 0);
                if (locx < 15) {
                    locx++;
                } else {
                    locx = 0;
                    locy++;
                }
            }

        }

//lots and lots of walls... we need to find a better way to do this
/*
        walls[0] = new Walls(120,0,40,360);
        walls[1] = new Walls(360,0,40,80);
        walls[2] = new Walls(560,0,40,320);
        walls[3] = new Walls(240,40,40,160);
        walls[4] = new Walls(440,40,40,80);
        walls[5] = new Walls(360,120,40,80);
        walls[6] = new Walls(480,160,40,120);
        walls[7] = new Walls(200,200,40,240);
        walls[8] = new Walls(360,240,240,40);
        walls[9] = new Walls(0,280,40,40);
        walls[10] = new Walls(360,280,40,40);
        walls[11] = new Walls(80,320,80,40);
        walls[12] = new Walls(440,320,40,160);
        walls[13] = new Walls(40,360,40,80);
        walls[14] = new Walls(520,360,120,40);
        walls[15] = new Walls(120,400,40,80);
        walls[16] = new Walls(160,400,40,40);
        walls[17] = new Walls(320,400,40,80);
        walls[18] = new Walls(400,400,40,80);
        walls[19] = new Walls(480,440,80,40);
*/
        }

//this is all of the movements, everything that should happen with every split second update
    public void tick(){
        //pongBall.tick();
        player.tick(grid,level);
        player.tick(grid,level);
        player.tick(grid,level);
        player.tick(grid,level);
    }
//renders the graphics... uses bs, buffered strategy to hold on to the g,
//graphics and then disposes the old graphics to be refilled later, then displays bs
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null){
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0,0,Game.WIDTH,Game.HEIGHT);
        player.render(g);
        //pongBall.render(g);
        for(int i = 0; i<grid[level].length;i++) grid[level][i].render(g);
        g.dispose();
        bs.show();
    }
/*
 *So... This one will take a little explaining:
 *A thread is a copy of this class only it has the ability to run two "threads"
 *concurrently. thread.start() calls two things:
   * game.start() and game.run()/thread.run()
 *it also cant call something that is already running so it runs the
 * run() and start() then waits then when run() + the timer is done it calls itself again
 *if you have questions read Thread API... its commented above the methods...
 */

    public synchronized void start() {
        if (isRunning) return;
        isRunning = true;
        thread = new Thread(this);
        thread.start();

    }
/*
 * stop works with the thread... basically it waits a certain amount of time
 *for a response that it already suspended. if it gets the response it will
 *throw and exception, if it doesn't it will end with a long of how long it
 *waited. the stop(empty) specifically waits 0 millis
 */
    public synchronized void stop(){
        if(!isRunning)return;
        isRunning=false;
        try{
            thread.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }
/*
 *this method reads 1 and 0 from .text files then returns them in an array to be used for creating levels
 */
    private static int[] read(String fileName)throws IOException{
        int[] arr = new int[192];
        FileReader file = new FileReader(fileName);
        int i=0;
        Scanner input = new Scanner(file);
        while(input.hasNext()){
            arr[i]= input.nextInt();
            i++;
        }
        return arr;
    }
// all of the implemented methods... runnable KeyListener...
    @Override
    public void run() {

//makes the JFrame look at you as if you clicked it
        requestFocus();
//fps used to display to screen... not much else
        int fps = 0;
//used for keeping up with fps... hold old time while the loop goes and counts times per second
        double timer = System.currentTimeMillis();
//used to store what time the system was at before and calculate the difference
        double lastTime = System.nanoTime();
//change this to change the fps
        double targetTick = 60.0;
//change in time from the last update
        double delta = 0;
// nano second... well target fps in nano seconds... sorta
        double ns = 1000000000/targetTick;
/*
 *this one is a little important
 * this while loop, while the program is still running (isRunning)
 * updates the picture and everything else
 * this is the central timer of the whole game
 */
        while(isRunning){
            double now = System.nanoTime();
            delta += (now - lastTime)/ ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                render();
                fps++;
                delta--;
            }
//literally only used to display the fps
            if(System.currentTimeMillis() - timer >= 1000){
                //System.out.print(fps);
                fps = 0;
                timer += 1000;
            }
        }
    }

    @Override
/*
 *If you don't know what KeyEvents and KeyListener is, you have a bit of reading to do
 *this is our control over the game
 *I used chars because something about e.getKeyCode() wasn't returning anything other than zero
 *if you could fix that it would allow us to use arrows and not have to worry about caps lock
 */
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() == 'd') player.d = true;
        if(e.getKeyChar() == 'a') player.a = true;
        if(e.getKeyChar() == 'w') player.w = true;
        if(e.getKeyChar() == 's') player.s = true;
        if(e.getKeyChar() == 'l'){
            System.out.println(player.xone);
            System.out.println(player.yone);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyChar() == 'd') player.d = false;
        if(e.getKeyChar() == 'a') player.a = false;
        if(e.getKeyChar() == 'w') player.w = false;
        if(e.getKeyChar() == 's') player.s = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}
