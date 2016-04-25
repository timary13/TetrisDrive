package tetrisDrive;

import java.io.*;


public class FileWorker {
    
    static String nameFile;
    
    public FileWorker(String fileName) {  
        
        nameFile = fileName;
            //Определяем файл
        File file = new File(nameFile);

        try {
            //проверяем, что если файл не существует то создаем его
            if(!file.exists()){
                file.createNewFile();
            }
        }catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void addText(String text){
        try{
            FileWriter sw = new FileWriter(nameFile,true);
            sw.write(text+"\n");
            sw.close();
        }catch(Exception e){
           System.out.print(e.getMessage());
       }  
    }
    
    public static String read() throws FileNotFoundException {
        //Этот спец. объект для построения строки
        StringBuilder sb = new StringBuilder();

        File file = new File(nameFile);
        if (!file.exists()){
            throw new FileNotFoundException(file.getName());
        }

        try {
            //Объект для чтения файла в буфер
            BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
            try {
                //В цикле построчно считываем файл
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            } finally {
                //Также не забываем закрыть файл
                in.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        //Возвращаем полученный текст с файла
        return sb.toString();
    }
       
    
}
