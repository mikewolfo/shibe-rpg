import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{

    //Boilerplate
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;
    public static final int SCALE = 3;
    public static final String TITLE = "Untitled Game";
    private static final BufferedImage screen = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    public static final Graphics2D paintbrush = screen.createGraphics();
    private boolean running = false;
    private static final long CLOCK_SPEED = 60L;
    private static final long NANOSECOND = 1000000000L;

    //In-game objects
    private ArrayList<Object> graphics = new ArrayList<Object>();

    //All objects that utilize key input
    private static ArrayList<KeyAdapter> controls = new ArrayList<KeyAdapter>();


    public Game(){
        Dimension size = new Dimension(WIDTH*SCALE, HEIGHT*SCALE);

        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        graphics.add(new LargeMap(2000,2000));
        graphics.add(new PlayableCharacter());

        
        
        controls.add(LargeMap.controller);
        controls.add(PlayableCharacter.controller);

    }

    public void start(){
        if(!running){
            running = true;
            new Thread(this).start();
        }
    }

    public void stop(){
        if(running)
            running = false;
    }

    public void tick(){
        for(int i = 0; i < graphics.size(); i++){
            ((Drawable) graphics.get(i)).tick();
        }

    }

    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        Drawable.clear();
        for(int i = 0; i < graphics.size(); i++){
            ((Drawable) graphics.get(i)).render();
        }

        g.drawImage(screen, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
        g.dispose();
        bs.show();
    }

    @Override
    public void run(){
        final double nsPerTick = NANOSECOND / CLOCK_SPEED;
        long lastTime = System.nanoTime();
        double unprocessedTime = 0;
        int frames = 0;
        int ticks = 0;
        long frameCounter = System.currentTimeMillis();


        while(running){
            long currentTime = System.nanoTime();
            long passedTime = currentTime - lastTime;
            lastTime = currentTime;
            unprocessedTime+=passedTime;

            if(unprocessedTime >= nsPerTick){
                unprocessedTime = 0;
                ticks++;
                tick();
            }

            render();
            frames++;

            if(System.currentTimeMillis() - frameCounter >= 1e3){
                System.out.println(frames + " frames, " + ticks + " ticks");
                ticks = 0;
                frames = 0;
                frameCounter+=1e3;
            }
        }
    }

    public static Game game;

    public static void main(String args[]){
        JFrame frame = new JFrame();
        game = new Game();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.pack();
        frame.setTitle(TITLE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setAlwaysOnTop(true);
        //game.addKeyListener(global);
        for(int i = 0; i < controls.size(); i++){
            game.addKeyListener(controls.get(i));
        }
        game.setFocusable(true);
        frame.setVisible(true);
        game.start();
        
    }

}
