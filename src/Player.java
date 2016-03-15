import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;


public class Player {
    //объявление констант
    public static final int MAX_SPEED = -40; //ограничение по максимальной скорости
    public static final int MAX_LEFT = 10;  //ограничение по ходу влево
    public static final int MAX_RIGHT = 280;  //ограничение по ходу вправо
    
    Image img = new ImageIcon ("resource/player_cut.png").getImage();  //загрузка картинки машины
    
    public Rectangle getRect(){
        return new Rectangle(x, y, 70, 160);
    }
    
    int speed = 0; //скорость
    int acceleration = 0;  //ускорение
    int lengthRoad = 0;  //длина пути
    
    int x = 270;  //начальное положение машины
    int y = 430;
    int dx = 0;  //смещенеие машины
    
    int layer1 = 0;  //"дорога" ч. 1
    int layer2 = -640;  //"дорога" ч. 2
    
    public void move(){
        lengthRoad += speed;
        speed -= acceleration;
        if (speed >= 0) speed = 0;  //если скорость станет отрицательной - остановка
        if (speed <= MAX_SPEED) speed = MAX_SPEED;  //чтобы скорость не превышала предельную
        x -= dx;
        if (x <= MAX_LEFT) x = MAX_LEFT;
        if (x >= MAX_RIGHT) x = MAX_RIGHT;
        //закцикленная "дорога"
        if (layer2 + speed >= 0){
            layer1 = 0;
            layer2 =- 640;
        }else{
            layer1 -= speed;
            layer2 -= speed;
        }
    }
    
    //обработка событий нажатия клавиш
    public void keyPressed (KeyEvent event){
        int key = event.getKeyCode();
        if (key == KeyEvent.VK_UP){
            acceleration = 1;
        }
        if (key == KeyEvent.VK_DOWN){
            acceleration = -1;
        }
        if (key == KeyEvent.VK_LEFT){
            dx = 10;
        }
        if (key == KeyEvent.VK_RIGHT){
            dx =- 10;
        }
    }
    //обработка событий отпускания клавиш
    public void keyReleased (KeyEvent event){
        int key = event.getKeyCode();
        if ((key == KeyEvent.VK_UP) || (key == KeyEvent.VK_DOWN)){
            acceleration = 0;
        }
        if ((key == KeyEvent.VK_RIGHT) || (key == KeyEvent.VK_LEFT)){
            dx = 0;
        }
    }
    
}
