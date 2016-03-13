import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.Timer;


public class Road extends JPanel implements ActionListener{
    
    Timer mainTimer = new Timer(20,this);  //каждые 20 мс запуск ф-и в объекте Road
    
    //Image imgSky = new ImageIcon("resource/bg-sky.jpg").getImage();
    Image imgRoad = new ImageIcon("resource/bg-road.jpg").getImage();
    
    Player p = new Player();
    
    public Road(){ //конструктор с запуском таймера
        mainTimer.start();
        addKeyListener(new MyKeyAdapter());
        setFocusable(true);
    }
    
    private class MyKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            p.keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
            p.keyReleased(e);
        }
    }
    
    public void paint(Graphics g){
        g=(Graphics2D)g;
        g.drawImage(imgRoad, 0, p.layer1, null);
        g.drawImage(imgRoad, 0, p.layer2, null);
        g.drawImage(p.img, p.x, p.y, null);
        
    }
    
    public void actionPerformed(ActionEvent e){
        p.move();
        repaint();
    }
}
