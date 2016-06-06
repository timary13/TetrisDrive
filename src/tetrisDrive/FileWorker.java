package tetrisDrive;

import java.awt.Rectangle;
import java.io.*;
import java.nio.file.Files;


public class FileWorker {
    
    static String nameFile;
    StringBuffer sb = new StringBuffer();
    File file;
    BufferedReader in;
    BufferedReader inCopy;
    
    public FileWorker(String fileName) {  
    	if(fileName == "")
    		nameFile = getListOfFiles();
    	else nameFile = fileName;
            //Определяем файл
        file = new File(nameFile);

        try {
            //проверяем, что если файл не существует то создаем его
            if(!file.exists()){
                file.createNewFile();
            }
        }catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openFile() {//throws FileNotFoundException 
    	
    	try{
    		in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
    	}catch(IOException e) {
            throw new RuntimeException(e);
        	}
    }
    
    public void closeFile() {
    	try{
    		in.close();
    	}catch(IOException e) {
            throw new RuntimeException(e);
        	}
    }
    
    public void addText(Rectangle rect, String _speed) {
    	
    	String str = new String( Integer.toString(rect.x) + " " + Integer.toString(rect.y) +" " + _speed + "\n");
    	
        try{
            FileWriter sw = new FileWriter(nameFile,true);
            sw.write(str);
            sw.close();
        }catch(Exception e){
           System.out.print(e.getMessage());
       } 
    }
    
    public void addString(String str) {
    	
    	sb.append(str+"\n");
    	
        try{
            FileWriter sw = new FileWriter(nameFile,true);
            sw.write(sb.toString());
            sw.close();
        }catch(Exception e){
           System.out.print(e.getMessage());
       }  
    }
    
    public String readAllInString() { //read all
        //Этот спец. объект для построения строки
        StringBuilder sb = new StringBuilder();
        String s;
        try{
        	s = inCopy.readLine();
            sb.append(s);
            sb.append("\n");
        } catch(IOException e) {
            throw new RuntimeException(e);
        	}

        return sb.toString();    	
    }
    
    public Rectangle readPosition() {
    	Rectangle pos = new Rectangle();
        String str;
        try{
        	str = in.readLine();
        	String[] retval = str.split(" ", 2);
    		pos.x = Integer.valueOf(retval[0]);
			pos.y = Integer.valueOf(retval[1]);
        } catch(IOException e) {
            throw new RuntimeException(e);
        	}
        return pos;
    }
  
    public String getListOfFiles(){
    	
        String files[] = new File("./logs").list();
        
        String newName = "logs/log"+(files.length+1)+".txt";
        
        for (int i=0; i<files.length;i++){
        	String s = files[i].toString();
        	String[] retval = s.split("-", 0);
        	if(retval.length == 1){
        		String nameDelFile = "logs/" + s;
        		File delFile = new File(nameDelFile);
        		if(delFile.delete())
        			System.out.println("delete file "+ nameDelFile);
        	}		
        }
        
        return newName;
    }
    
    public void renameLog(String result) {
    	String[] retval = nameFile.split("\\.", 0);
    	String resultNewNameFile = new String(retval[0] + "-" + result +  "." + retval[1]);
    	
    	File newFile = new File(resultNewNameFile);
        try {
			Files.copy(file.toPath(), newFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
    
    
    public String getNameFile(){
    	return nameFile;
    }
  
}
