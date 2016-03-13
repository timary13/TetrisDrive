import javax.swing.*;

public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame f=new JFrame("Java racing");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(370, 640);
        f.add(new Road());
        f.setVisible(true);
        
    }
    
}
