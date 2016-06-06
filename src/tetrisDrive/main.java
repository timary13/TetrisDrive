package tetrisDrive;

import java.awt.Color;
import java.awt.Font;
//import com.sun.glass.ui.Size;
import java.io.FileNotFoundException;
import javax.swing.*;

public class main {

	
	/**Кнопка иры бота*/
	private JButton extraButton;
	/**Кнопка игры пользователя*/
	private JButton gameButton;
	/**Кнопка выхода из игры*/
	private JButton exitButton;
	
	/**Кнопка выбора лёгкого уровня*/
	private JRadioButton easyLevelsButton;
	/**Кнопка выбора среднего уровня*/
	private JRadioButton mediumLevelsButton;
	/**Кнопка выбора сложного уровня*/
	private JRadioButton hardLevelsButton;
	/**Группа кнопок выбора уровня*/
	private ButtonGroup chooseLevelsGroup;
	/**Box всех элеметов окна*/
	private Box mainBox;

    public static void main(String[] args)  throws FileNotFoundException {//static <Size>
        //JFrame.setDefaultLookAndFeelDecorated(true);
        
        JFrame frame = new JFrame("Java racing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(370, 640);
        //frame.add(new Road());
        frame.add(new Menu(frame));
        
        //frame.setVisible(true);

    }
    
}
