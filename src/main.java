import javax.swing.*;

public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Java racing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(370, 640);
        frame.add(new Road());
        frame.setVisible(true);
        
    }
    
}
