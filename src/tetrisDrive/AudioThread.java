package tetrisDrive;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class AudioThread implements Runnable {

    
    String file;
    Player p;
    
    public AudioThread(String s){
        file=s;
    }
    
    //
    public void repeating(){
        try{
            p.close();
            p.play();
        } catch(JavaLayerException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run(){
        
        try{
            p = new Player(new FileInputStream(file));
            p.play();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch(JavaLayerException e) {
            e.printStackTrace();
        }
        }
}
