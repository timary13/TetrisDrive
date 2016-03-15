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
    
    Timer mainTimer = new Timer (20,this);  //каждые 20 мс запуск ф-и в объекте Road
    
    Image imgRoad = new ImageIcon ("resource/bg-road.jpg").getImage();
    
    Player player = new Player();
    
    Thread enemiesFactory = new Thread(this);  //создаем поток ||программе
   List<Enemy> enemies = new ArrayList<Enemy>();  //создали коллекцию(список)
    
    public Road(){ //конструктор с запуском таймера
        mainTimer.start();
        enemiesFactory.start();
        addKeyListener (new MyKeyAdapter());
        setFocusable(true);
    }
    
    private class MyKeyAdapter extends KeyAdapter{
        public void keyPressed(KeyEvent event){
            player.keyPressed(event);
        }
        public void keyReleased(KeyEvent event){
            player.keyReleased(event);
        }
    }
    
    public void paint(Graphics graphic){
        graphic=(Graphics2D)graphic;
        graphic.drawImage(imgRoad, 0, player.layer1, null);
        graphic.drawImage(imgRoad, 0, player.layer2, null);
        graphic.drawImage(player.img, player.x, player.y, null);
        
        Iterator<Enemy> i = enemies.iterator();  //пробег по списку препятствий
        Iterator<Enemy> j;
        boolean emptyPlace;
        while(i.hasNext()){
            Enemy nextEnemy = i.next();
            if((nextEnemy.y >= 640)||(nextEnemy.y <= -640)){ //удаление из списка при выходе за границы поля
                i.remove();
            }else{
                nextEnemy.move();
                  emptyPlace=true;
//                j = enemies.iterator();
//                int f=0;//
//                while(j.hasNext()){
//                    f++;
//                    System.out.println("size="+enemies.size()+" f="+f);
//                    Enemy newEnemy = j.next();
//                    if(newEnemy.getRect().intersects(nextEnemy.getRect())&&nextEnemy!=newEnemy){
//                        emptyPlace=false;
//                    }
//                }
                //System.out.println("emptyPlace? "+ emptyPlace);
                if( (!nextEnemy.getRect().intersects(player.getRect())) && emptyPlace){
                    graphic.drawImage(nextEnemy.img, nextEnemy.x, nextEnemy.y, null);  //прорисовка препятствия
                }
            } 
        }
    }
    
    public void actionPerformed(ActionEvent event){
        player.move();  //обновление движения игрока
        repaint(); //перерисовка соперников
        testCollisionWithEnemies();
    }
    
    public void testCollisionWithEnemies(){
        Iterator <Enemy> i = enemies.iterator();
        while(i.hasNext()){
            Enemy enemy=i.next();
            if(player.getRect().intersects(enemy.getRect())){ //если прямоуголиник пересекаются
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
            }catch(InterruptedException exception){
                exception.printStackTrace();
            }
            
        }
    }
}
