package tetrisDrive;


//import com.sun.awt.AWTUtilities;
//import com.sun.glass.ui.Size;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Menu extends JPanel{
    
    //Size size;
    
    public Menu(){
        
        //size = _size;

        JFrame.setDefaultLookAndFeelDecorated(true);
        createGUI();
    }
    
     public static void createGUI() {
          JFrame transframe = new JFrame("Test frame");
          transframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          
          JPanel panel = new JPanel();
          panel.setLayout(new FlowLayout());
          JButton button1 = new JButton("Button1");
          button1.setActionCommand("Button1 was pressed!");
          panel.add(button1);
          
          //ActionListener actionListener = new TestActionListener();
          
          //button1.addActionListener(actionListener);
          
          //getContentPane().add(panel);
          
          //transframe.getContentPane().add(label);
            transframe.getContentPane().add(panel);
          
          transframe.setPreferredSize(new Dimension(370, 640));
          transframe.pack();
          //transframe.setLocationRelativeTo(null);
          transframe.setVisible(true);

          //AWTUtilities.setWindowOpacity(transframe, 0.5f); //прозрачность окна
     }
     
    
}




