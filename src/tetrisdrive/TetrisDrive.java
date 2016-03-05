package tetrisdrive;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class TetrisDrive extends JPanel{
        
        
    private Color[][] field;
        
    private void init() {
        field = new Color[13][20];
        for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 20; j++) {
                        if (i == 0 || i == 12 || j == 19||j==0) {
                                field[i][j] = Color.GRAY;
                        } else {
                                field[i][j] = Color.BLACK;
                        }
                }
        }
    }
    
         
    @Override 
    public void paintComponent(Graphics g)
    {
        // Paint the field
        g.fillRect(0, 0, 26*13, 26*20);
        for (int i = 0; i < 13; i++) {
                for (int j = 0; j < 20; j++) {
                        g.setColor(field[i][j]);
                        g.fillRect(26*i, 26*j, 25, 25);
                }
        }

    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Tetris");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(13*26+20, 20*26+40);
		f.setVisible(true);
                
                TetrisDrive game=new TetrisDrive();
                game.init();
                f.add(game);
    }
    
}
