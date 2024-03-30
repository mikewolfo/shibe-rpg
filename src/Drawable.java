import java.awt.Color;
import java.awt.Graphics2D;

public interface Drawable {
    

final Graphics2D pb = Game.paintbrush;
final int canvasWidth = Game.WIDTH;
final int canvasHeight = Game.HEIGHT;
public void tick();
public void render();

    public static void clear(){
        Color oldColor = pb.getColor();
        pb.setColor(Color.BLACK);
        pb.fillRect(0,0,canvasWidth, canvasHeight);
        pb.setColor(oldColor);
    }

}
