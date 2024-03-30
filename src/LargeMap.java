import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class LargeMap implements Drawable{
    
    /*
     *     Each tile is 32x24 pixels
     */


    private int mapWidth;
    private int mapHeight;
    public static KeyAdapter controller;
    private final int PX_WIDTH = 32;
    private final int PX_HEIGHT = 24;
    private final int colorOpts = (int) Math.pow(256, 3);
    private int[][] colors;
    int x = 0, y = 0;
    int xv = 0, yv = 0;

    public static boolean movingX = false;
    public static boolean movingY = false;

    public LargeMap(int mapWidth, int mapHeight){
        controller = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                char key = e.getKeyChar();
                    
                if(key == 'w')
                    yv = 4;
                if(key == 'a')
                    xv = 4;
                if(key == 's')
                    yv = -4;
                if(key == 'd')
                    xv = -4;
                
                
            }

            @Override
            public void keyReleased(KeyEvent e){
                switch(e.getKeyChar()){
                    case 'w':
                    case 's':
                    yv = 0;
                    break;
                    case 'a':
                    case 'd':
                    xv = 0;
                    break;
                }
            }
        };

        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        colors = new int[mapWidth][mapHeight];

        
        resetColors();
        
    }

    public void resetColors(){
        Random random = new Random();

        for(int i = 0; i < mapWidth; i++){
            for(int j = 0; j < mapHeight; j++){
                int randNum = random.nextInt(colorOpts);
                colors[i][j] = (randNum & 0xFFFFFF)  ;
            }
        }
    }

    public void tick(){
        if( movingX && (xv < 0 && x + (PX_WIDTH * mapWidth - canvasWidth) > 0 || (xv > 0 && x < 0)) )
            x+=xv;
        if(( movingY && (yv < 0 && y + (PX_HEIGHT * mapHeight - canvasHeight) > 0 || (yv > 0 && y < 0)) ))
            y+=yv;
        
        // resetColors();

    }

    public void render(){

        int ii = x / PX_WIDTH * -1;
        int ji = y / PX_HEIGHT * -1;

        for(int i = ii; i <= ii + 10; i++){
            for(int j = ji; j <= ji + 10; j++){
                try{
                    pb.setColor(new Color(colors[i][j]));
                    pb.fillRect(i*PX_WIDTH + x, j*PX_HEIGHT + y, PX_WIDTH, PX_HEIGHT);
                }catch(ArrayIndexOutOfBoundsException e){
                    System.err.println("Border of map reached!");
                }
                
            }
        }
    }



}
