
package tetrisDrive;


import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

public class Enemy {
    
    int x;
    int y;
    int speed;
    Image img = new ImageIcon ("resource/enemy_cut.png").getImage();
    RoadBot roadBot;
    RoadPlayer roadPlayer;
    RoadReplay roadReplay;
    Random rand = new Random();
	int enemySleep = rand.nextInt(4000);
	int enemyX = rand.nextInt(280);
	int enemySpeed = -rand.nextInt(20)-10;
	String s;
    
    FileWorker logFile;
    
    public Rectangle getRect(){
        return new Rectangle(x, y, 80, 170);
    }
    
    public Enemy (RoadBot road, String _nameFile){ /**конструктор*/
    	logFile = new FileWorker(_nameFile);
    	
    	this.y=-510;
        this.roadBot = road;
    	s = roadBot.getNameClass();
    	
        this.x=enemyX;
        this.speed = enemySpeed; 
        setLog();
        
    }
    
    public Enemy (RoadReplay road, String _nameFile){ /**конструктор*/
    	logFile = new FileWorker(_nameFile);
    	
    	this.y=-510;
        this.roadReplay = road;
    	s = roadReplay.getNameClass();
    	
        this.x=enemyX;
        this.speed = enemySpeed; 
        setLog();
        
    }
    
    public Enemy (RoadPlayer road, String _nameFile){ /**конструктор*/
    	logFile = new FileWorker(_nameFile);
    	
    	this.y=-510;
        this.roadPlayer = road;
    	s = roadPlayer.getNameClass();
    	
        this.x=enemyX;
        this.speed = enemySpeed; 
        setLog();
        
    }

    public Enemy (String _log, RoadBot road) {
    	this.roadBot = road;
    	s = roadBot.getNameClass();
    	
    	String[] retval = _log.split(" ", 0);
    	if(retval.length > 2) {
    		this.y=-510;
    		this.x = Integer.valueOf(retval[2]);
			this.speed = Integer.valueOf(retval[3]);
			this.enemySleep = Integer.valueOf(retval[1]);
    	}
    }
    
    public Enemy (String _log, RoadPlayer road) {
    	this.roadPlayer = road;
    	s = roadPlayer.getNameClass();
    	
    	String[] retval = _log.split(" ", 0);
    	if(retval.length > 2) {
    		this.y=-510;
    		this.x = Integer.valueOf(retval[2]);
			this.speed = Integer.valueOf(retval[3]);
			this.enemySleep = Integer.valueOf(retval[1]);
    	}
    }
    
    public Enemy (String _log, RoadReplay road) {
    	this.roadReplay = road;
    	s = roadReplay.getNameClass();
    	
    	String[] retval = _log.split(" ", 0);
    	if(retval.length > 2) {
    		this.y=-510;
    		this.x = Integer.valueOf(retval[2]);
			this.speed = Integer.valueOf(retval[3]);
			this.enemySleep = Integer.valueOf(retval[1]);
    	}
    }
    
    public void move(){
    	if(s.equals("RoadBot"))
    		y=y+speed-roadBot.player.speed; //Robot
    	else if(s.equals("RoadPlayer"))
    		y=y+speed-roadPlayer.player.speed; //Player
    	else if(s.equals("RoadReplay"))
    		y=y+speed-roadReplay.player.speed; //Replay
    }
   
    public void setLog(){
    	
        StringBuilder sb = new StringBuilder();
        sb.append("e "+enemySleep + " ");
        sb.append(enemyX + " ");
        sb.append(enemySpeed);
        logFile.addString(sb.toString());
    }
    
}
