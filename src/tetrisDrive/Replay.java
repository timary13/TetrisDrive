package tetrisDrive;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Replay extends Player implements Runnable{
	
	Thread replay = new Thread (this);

	  /**объявление констант*/
	  public static final int MAX_SPEED = -40; //ограничение по максимальной скорости
	  public static final int MAX_LEFT = 10;  //ограничение по ходу влево
	  public static final int MAX_RIGHT = 280;  //ограничение по ходу вправо
	  
	    Image img = new ImageIcon ("resource/player_cut.png").getImage();  /**загрузка картинки машины*/
	
	  int speed = 0; //скорость
	  int acceleration = 0;  //ускорение
	  int lengthRoad = 0;  //длина пути
	  
	  int x = 270;  //начальное положение машины
	  int y = 430;
	  int dx = 0;  //смещенеие машины
	  
	    int xEnemy = -500;//
	    int yEnemy = -510;//
	    int speedEnemy = 0;
	  
	  
	  int layer1 = 0;  //"дорога" ч. 1
	  int layer2 = -640;  //"дорога" ч. 2
	  RoadReplay road;
	  
	  FileWorker logFile;
	  BufferedReader in;
	  BufferedReader inEnemy;
	  String logEnemy = "";
	  List <String> logsEnemyList;
	  String[] logsEnemy;
	  File file;
	  int firstDelay;
	      
    public Rectangle getRect(){
        return new Rectangle(x, y, 70, 160);
    }
	  
	  
	public Replay(RoadReplay _road){
		road = _road;
		
		String path = "logs/"+road.nameLog;
		logsEnemyList = new ArrayList <String>();
	      file = new File(path);
	      
	      logFile = new FileWorker(path);
	      
	    	try{
	    		in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
	    		inEnemy = new BufferedReader(new FileReader( file.getAbsoluteFile()));
	    	}catch(IOException e) {
	            throw new RuntimeException(e);
	        	}
	    	replay.start();
	    	getEnemyMas();
	}

	
	  public void move(){
	        lengthRoad += speed;
	        speed -= acceleration;
	        if (speed >= 0) speed = 0;  /**если скорость станет отрицательной - остановка*/
	        if (speed <= MAX_SPEED) speed = MAX_SPEED;  //чтобы скорость не превышала предельную
	        x -= dx;
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

	  
	  public void getEnemyMas() {
      String str;
      try{
    	  do{  
    		  str = inEnemy.readLine();
    		  if(str==null)
    			  break; 
        	  String[] retval = str.split(" ", 0);
        	  if(retval.length > 3)
        		  logsEnemyList.add(str);
    	  }while(str != null);
	  }catch(IOException e) {
          throw new RuntimeException(e);
  	}
      logsEnemy = new String[logsEnemyList.size()];
      logsEnemy = logsEnemyList.toArray(logsEnemy);
  	try{
		inEnemy.close();
	}catch(IOException e) {
        throw new RuntimeException(e);
    	}
  	System.out.println("logsEnemy:");
  	for(int i=0; i<logsEnemy.length;i++)
  		System.out.println(logsEnemy[i]);
  	
      System.out.println("logsEnemy.length="+logsEnemyList.size());
  }

	    
    public void getLog(){

        String str;
    do {
        try{
        	str = in.readLine();
  		  if(str==null)
			  break; 
        	String[] retval = str.split(" ", 0);
        	if(retval.length == 3) {
        		x = Integer.valueOf(retval[0]);
    			y = Integer.valueOf(retval[1]);
    			speed = Integer.valueOf(retval[2]);
    			System.out.println("get: x="+x+" y="+y+"speed="+speed+"\n");
		      try{
		          Thread.sleep(30);
		      }catch(InterruptedException exception){
		          exception.printStackTrace();
		      }
        	}
        	else{
    			logEnemy = str;
        	}
        } catch(IOException e) {
            throw new RuntimeException(e);
        	}
    } while(str != null);
    
	try{
		in.close();
	}catch(IOException e) {
        throw new RuntimeException(e);
    	}
        	
    }
    

    
    public String getFileName(){
  	  return logFile.getNameFile();
    }
    
    
    @Override
    public void run(){ /**точка входа в поток*/
        try{
        	
            Thread.sleep(0);
            getLog();
            System.out.println("run()");
        }catch(InterruptedException exception){
            exception.printStackTrace();
        }
    }
    
    
    
    public String getLogEnemy() {
    	return logEnemy;
    }
    
    public String getNameClass(){
    	return (new String("Replay"));
    }
}
