package tetrisDrive;

//import com.sun.glass.ui.Size;
import java.io.FileNotFoundException;
import javax.swing.*;

public class main {


    public static <Size> void main(String[] args)  throws FileNotFoundException {
        //JFrame.setDefaultLookAndFeelDecorated(true);
        
        JFrame frame = new JFrame("Java racing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Size size = new Size(370, 640);
        frame.setSize(370, 640);
        frame.add(new Road());
        //frame.add(new Menu(size));
        //Menu menu = new Menu(size);
        frame.setVisible(true);

    }
    
}
