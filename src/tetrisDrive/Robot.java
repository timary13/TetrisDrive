package tetrisDrive;

//иногда зацикливается между поворотом вправо и влево
import java.awt.Image;
import java.util.List;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;


public class Robot extends Player implements  ActionListener, Runnable{
  
  Timer secondaryTimer = new Timer(40, this); //30
  Thread bot = new Thread (this);
  
  /**объявление констант*/
  public static final int MAX_SPEED = -40; //ограничение по максимальной скорости
  public static final int MAX_LEFT = 10;  //ограничение по ходу влево
  public static final int MAX_RIGHT = 280;  //ограничение по ходу вправо
  
  Image img = new ImageIcon ("resource/player_cut.png").getImage();  /**загрузка картинки машины*/
  
  String fileBrake = "resource/brake.mp3";
  AudioThread brake = new AudioThread(fileBrake);
  String fileGainSpeed = "resource/gainSpeed.mp3";
  AudioThread gainSpeed = new AudioThread(fileGainSpeed);
  
  Thread audioBrake;
  Thread audioGainSpeed;
  
  public Rectangle getRect(){
      return new Rectangle(x, y, 70, 160);
  }
  
  public Rectangle getUpRect(){
  return new Rectangle(x, y-50, 70, 160);
  }
  
  public Rectangle getLeftRect(){
  return new Rectangle(x-70, y, 70, 160);
  }
  
  public Rectangle getRightRect(){
  return new Rectangle(x+70, y, 70, 160);
  }
  
  int speed = 0; //скорость
  int acceleration = 0;  //ускорение
  int lengthRoad = 0;  //длина пути
  
  int x = 270;  //начальное положение машины
  int y = 430;
  int dx = 0;  //смещенеие машины
  
  int layer1 = 0;  //"дорога" ч. 1
  int layer2 = -640;  //"дорога" ч. 2
  
  List<Enemy> enemies;
  
  int loopRight; /**для определения зацикливания бота в выборе перемещения*/
  int loopLeft;
  
  StringBuffer sb = new StringBuffer();
  
  public Robot(List<Enemy> _enemies){
      FileWorker logFile = new FileWorker("logs/log.txt");
      loopRight = 0;
      loopLeft  = 0;
      
      enemies = _enemies;
      secondaryTimer.start();
      bot.start();
  }
  
  public void saveLog(boolean win){
      if(win) sb.append("5");
      else sb.append("0");
     logFile.addText(sb.toString()); 
  }
  
  public void move(){
      lengthRoad += speed;
      speed -= acceleration;
      if (speed >= 0) speed = 0;  /**если скорость станет отрицательной - остановка*/
      if (speed <= MAX_SPEED) speed = MAX_SPEED;  /**чтобы скорость не превышала предельную*/
      x += dx;
      if (x <= MAX_LEFT) x = MAX_LEFT;
      if (x >= MAX_RIGHT) x = MAX_RIGHT;
      /**закцикленная "дорога"*/
      if (layer2 + speed >= 0){
          layer1 = 0;
          layer2 =- 640;
      }else{
          layer1 -= speed;
          layer2 -= speed;
      }
  }
  
  
  public void actionPerformed(ActionEvent event){
      testChanceCollision();
  }
  
   public void testChanceCollision(){
      Iterator <Enemy> i = enemies.iterator();
      while(i.hasNext()){
          Enemy enemy = i.next();
          if(getUpRect().intersects(enemy.getRect())){ /**если прямоуголиник пересекаются*/
              speed += 10; //уменьшаем скорость
              sb.append("3");
              if (testLeftFree() && loopLeft == 0) {
                  x -= 70;
                  sb.append("4");
                  if(loopRight > 0)
                  loopLeft++;
              }
                  else if (testRightFree() && loopRight <= 2){
                      x += 70;
                      sb.append("2");
                      loopRight++;
                  }
                      else {
                      speed += 10;
                      sb.append("3");
                      loopLeft = 0;
                      loopRight = 0;
                  }
          }
          else {
              speed -= 1; //ускоряемся
              if (speed > MAX_SPEED+20) sb.append("1");
          }  
      }
  }
   
  public boolean testLeftFree(){
      Iterator <Enemy> i = enemies.iterator();
      while(i.hasNext()){
          Enemy enemy=i.next();
          if(getLeftRect().intersects(enemy.getRect())){ /**если прямоуголиник пересекаются*/
              //System.out.println("Left not free");
              return false;
          }  
      }
      if ((x-70) <= MAX_LEFT) return false;
      return true;
   }
  
  public boolean testRightFree(){
      Iterator <Enemy> i = enemies.iterator();
      while(i.hasNext()){
          Enemy enemy=i.next();
          if(getRightRect().intersects(enemy.getRect())){ /**если прямоуголиник пересекаются*/
              //System.out.println("Right not free");
              return false;
          } 
      }
      if ((x+70) >= MAX_RIGHT) return false;
      return true;
   }
  
  @Override
  public void run(){ /**точка входа в поток*/
      try{
          Thread.sleep(2000);
      }catch(InterruptedException exception){
          exception.printStackTrace();
      }
  }
}
