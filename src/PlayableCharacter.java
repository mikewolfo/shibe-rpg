import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayableCharacter implements Drawable{
    
    public static KeyAdapter controller;

    int PX_WIDTH = 32;
    int PX_HEIGHT = 24;

    final int PLAYER_VELOCITY = 4;

    int xo = PX_WIDTH / 2;
    int yo = PX_HEIGHT / 2;


    //respective to the center of the character
    int x = canvasWidth / 2;
    int y = canvasHeight / 2;



    //x and y velocities
    int xv = 0;
    int yv = 0;

    
    int trueX = x - xo; 
    int trueY = y - yo;


    boolean up = false;
    boolean down = false;
    boolean left = false;
    boolean right = false;

    public PlayableCharacter(){
        controller = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                char key = e.getKeyChar();
                    
                if(key == 'w')
                    up = true;
                if(key == 'a')
                    left = true;
                if(key == 's')
                    down = true;
                if(key == 'd')
                    right = true;
                
                
            }

            @Override
            public void keyReleased(KeyEvent e){
                char key = e.getKeyChar();
                    
                if(key == 'w')
                    up = false;
                if(key == 'a')
                    left = false;
                if(key == 's')
                    down = false;
                if(key == 'd')
                    right = false;
            }
        };
    }

    @Override
    public void tick() {
        
        if( (xv < 0 && trueX > PX_WIDTH) || (xv > 0 && trueX < PX_WIDTH * 8) ){
            LargeMap.movingX = false;
            trueX+=xv;
        }else if(xv != 0){
            LargeMap.movingX = true;
        }
        

        if( (yv < 0 && trueY > PX_HEIGHT) || (yv > 0 && trueY < PX_HEIGHT * 8) ){
            LargeMap.movingY = false;
            trueY+=yv;
        }else if(yv != 0){
            LargeMap.movingY = true;
        }
        
    }

    @Override
    public void render() {
        pb.setColor(Color.BLACK);
        pb.fillOval(trueX, trueY, PX_WIDTH, PX_HEIGHT);
    }

    
    



}
