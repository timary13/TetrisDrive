package tetrisDrive;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;


public class RoadPlayer extends JPanel implements ActionListener, Runnable{
    
    Timer mainTimer = new Timer (30,this);  /**каждые 20 мс запуск ф-и(actionPerformed) в объекте Road*/
    
    Image imgRoad = new ImageIcon ("resource/bg-road.jpg").getImage();
   
    
    Thread enemiesFactory = new Thread(this);  /**создаем поток ||программе*/
   List<Enemy> enemies = new ArrayList<Enemy>();  /**создали коллекцию(список)*/
   
    Player player = new Player();
    
    
    
   String fileStarter = "resource/starter.mp3";
   Thread audioStarter = new Thread( new AudioThread(fileStarter));
   String fileBackground = "resource/background.mp3";
   Thread audioBackground = new Thread ( new AudioThread(fileBackground));
           
    public RoadPlayer() { /**конструктор с запуском таймера*/
    		
    	String s = player.getNameClass();
    	if(s.equals("Player") || s.equals("Robot")) {
    		player.openF();
    		System.out.println("openF()");
    	}
        mainTimer.start();
        audioStarter.start();
        //audioBackground.start();
        enemiesFactory.start();
        addKeyListener (new MyKeyAdapter());
        setFocusable(true);
    }

    /**проверка на победу*/
    private void testWin() {
        if(player.lengthRoad < -32000){ //640*50
            JOptionPane.showMessageDialog(null, "Вы выиграли!");
            String s = player.getNameClass();
            if( s.equals("Player") || s.equals("Robot"))
            	player.logFile.renameLog("victory");
            System.exit(0);
        }
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
        
        double speed = (200/Player.MAX_SPEED)*player.speed;
        graphic.setColor(Color.white);
        Font font = new Font("Arial", Font.ITALIC, 20);
        graphic.setFont(font);
        graphic.drawString("Скорость: " + speed +" км/ч", 30, 30);
        
        Iterator<Enemy> i = enemies.iterator();  /**пробег по списку препятствий*/
        Iterator<Enemy> j;
        boolean emptyPlace;
        while(i.hasNext()){
            Enemy nextEnemy = i.next();
            if((nextEnemy.y >= 640)||(nextEnemy.y <= -640)){ /**удаление из списка при выходе за границы поля*/
                i.remove();
            }else{
                emptyPlace=true;
                j = enemies.iterator();
                while(j.hasNext()){
                    Enemy newEnemy = j.next();
                    if(newEnemy.getRect().intersects(nextEnemy.getRect())&&nextEnemy!=newEnemy){ /**пересекает другого соперника кроме себя?*/
                        emptyPlace = false;
                        i.remove();
                    }
                }
                if( ( !nextEnemy.getRect().intersects(player.getRect()) )){ /**пересекает игрока?*/
                    nextEnemy.move();
                    graphic.drawImage(nextEnemy.img, nextEnemy.x, nextEnemy.y, null);  /**прорисовка препятствия*/
                    
                }
            } 
        }
    }
    
    
    public void actionPerformed(ActionEvent event){
        player.move();  /**обновление движения игрока*/
        repaint(); /**перерисовка соперников*/
        
    	String s = player.getNameClass();
    	if( s.equals("Player") || s.equals("Robot"))
    		getLog();
	
        
        testCollisionWithEnemies(); /**есть ли столкновения?*/
        testWin(); /**победа?*/
    }
    
    public void getLog() {//throws FileNotFoundException
		player.saveLog(true);
    }
    
    
    public void testCollisionWithEnemies(){
        String fileCollision = "resource/collision.mp3";
        
        Iterator <Enemy> i = enemies.iterator();
        while(i.hasNext()){
            Enemy enemy=i.next();
            if(player.getRect().intersects(enemy.getRect())){ /**если прямоуголиник пересекаются*/
                Thread audioCollision = new Thread( new AudioThread(fileCollision));
                audioCollision.start();
                JOptionPane.showMessageDialog(null,"Вы проиграли!");
                String s = player.getNameClass();
                if( s.equals("Player") || s.equals("Robot"))
                	player.logFile.renameLog("loss");
                System.exit(0);
            }
        }
    }
    
    @Override
    public void run(){ /**точка входа в поток*/
    	String s = player.getNameClass();
        while(true){
            try{       	
            	if(s.equals("Replay")){
            		for(int i=0; i<player.logsEnemy.length; i++){
    	                System.out.println("sleep enemy[i]=" + i);
            			String str = player.logsEnemy[i];
            			Enemy newEnemy = new Enemy(str, this);
            			Thread.sleep(newEnemy.enemySleep);
            			enemies.add(newEnemy);
            		}	
            		//System.exit(0);
            	}
            	if(s.equals("Robot") || s.equals("Player")) {
	                Enemy newEnemy = new Enemy(this, player.getFileName());
	                Thread.sleep(newEnemy.enemySleep);//newEnemy.enemySleep
	                enemies.add(newEnemy);
            	}
                  //создание препятсвий и закидывание их в ArrayList
            }catch(InterruptedException exception){
                exception.printStackTrace();
            }
            
        }
    }
    
    public String getNameClass(){
    	return (new String("RoadPlayer"));
    }
}

