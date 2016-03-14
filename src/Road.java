import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;


public class Road extends JPanel implements ActionListener, Runnable{
    
    Timer mainTimer = new Timer(20,this);  //каждые 20 мс запуск ф-и в объекте Road
    
    //Image imgSky = new ImageIcon("resource/bg-sky.jpg").getImage();
    Image imgRoad = new ImageIcon("resource/bg-road.jpg").getImage();
    
    Player p = new Player();
    
    Thread enemiesFactory=new Thread(this);  //создаем поток ||программе
   List<Enemy> enemies = new ArrayList<Enemy>();  //создали коллекцию(список)
    
    public Road(){ //конструктор с запуском таймера
        mainTimer.start();
        enemiesFactory.start();
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
        
        Iterator<Enemy> i = enemies.iterator();  //пробег по списку препятствий
        while(i.hasNext()){
            Enemy e= i.next();
            if(e.y>=640||e.y<=-640){ //удаление из списка при выходе за границы поля
                i.remove();
            }else{
                e.move();
                g.drawImage(e.img, e.x, e.y, null);  //прорисовка препятствия
            } 
        }
    }
    
    public void actionPerformed(ActionEvent e){
        p.move();  //обновление движения игрока
        repaint(); //перерисовка соперников
        testCollisionWithEnemies();
    }
    
    public void testCollisionWithEnemies(){
        Iterator<Enemy> i = enemies.iterator();
        while(i.hasNext()){
            Enemy e=i.next();
            if(p.getRect().intersects(e.getRect())){ //если прямоуголиник пересекаются
                //i.remove();
                JOptionPane.showMessageDialog(null,"Вы проиграли!");
                System.exit(1);
            }
        }
    }
    
    @Override
    public void run(){ ///точка входа в поток
        while(true){
            Random rand = new Random();
            try{
                Thread.sleep(rand.nextInt(2000));
                enemies.add(new Enemy(rand.nextInt(280),-510, -10,this));  //создание препятсвий и закидывание их в ArrayList
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            
        }
    }
}
