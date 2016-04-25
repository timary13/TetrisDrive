package tetrisDrive;


import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Enemy {
    
    int x;
    int y;
    int speed;
    Image img = new ImageIcon ("resource/enemy_cut.png").getImage();
    Road road;
    
    public Rectangle getRect(){
        return new Rectangle(x, y, 80, 170);
    }
    
    public Enemy (int x, int y, int speed, Road road){ /**конструктор*/
        this.x=x;
        this.y=y;
        this.speed = speed;
        this.road = road;
        //System.out.println("speed of enemy="+speed);
    }
    
    public void move(){
        y=y+speed-road.player.speed;
    }
   
}
