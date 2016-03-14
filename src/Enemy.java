
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Enemy {
    
    int x;
    int y;
    int speed;
    Image img = new ImageIcon("resource/enemy.png").getImage();
    Road road;
    
    public Rectangle getRect(){
        return new Rectangle(x, y, 87, 178);
    }
    
    public Enemy(int x, int y, int speed, Road road){ //конструктор
        this.x=x;
        this.y=y;
        this.speed=speed;
        this.road=road;
    }
    
    public void move(){
        y=y-road.p.speed+speed;
    }
    
}
