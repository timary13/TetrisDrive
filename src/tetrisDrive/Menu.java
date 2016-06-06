package tetrisDrive;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
//import com.sun.awt.AWTUtilities;
//import com.sun.glass.ui.Size;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;


public class Menu extends JPanel{
    
	static JFrame frame;
	JFrame framePlay;
	JFrame frameList;
	JFrame frameTransLog;
	JFrame frameStat;
	
	/**Кнопки игры*/
	private JButton botButton;
	private JButton gameButton;
	private JButton exitButton;
	private JButton replayButton;
	private JButton getStatButton;
	
	int playingShot = 0;
	
	
    public Menu(JFrame _frame){
    	
    	frame = _frame;
    	frame.setResizable(false);
        //JFrame.setDefaultLookAndFeelDecorated(true);
        createGUI();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }


	/**Создание кнопок*/
	private void createButtons(){
		JPanel panel = new JPanel();
		
		replayButton = new JButton("Games");
		panel.add(replayButton);
		botButton = new JButton("Bot");
		panel.add(botButton);
		gameButton = new JButton("Play");
		panel.add(gameButton);
		exitButton = new JButton("Exit");
		panel.add(exitButton);
		getStatButton = new JButton("Statistics");
		panel.add(getStatButton);
		
		botButton.setFont(new Font(null, Font.BOLD,14));
		gameButton.setFont(new Font(null, Font.BOLD,14));
		exitButton.setFont(new Font(null, Font.BOLD,14));
		replayButton.setFont(new Font(null, Font.BOLD,14));
		getStatButton.setFont(new Font(null, Font.BOLD,14));
		
		botButton.setBackground(Color.GRAY);
		gameButton.setForeground(Color.LIGHT_GRAY);
		gameButton.setBackground(Color.DARK_GRAY);
		exitButton.setBackground(Color.DARK_GRAY);
		exitButton.setForeground(Color.LIGHT_GRAY);
		replayButton.setBackground(Color.GRAY);
		getStatButton.setBackground(Color.GRAY);
		
		botButton.setPreferredSize(new Dimension(123,50));
		gameButton.setPreferredSize(new Dimension(200,50));
		exitButton.setPreferredSize(new Dimension(200,50));
		replayButton.setPreferredSize(new Dimension(123,50));
		getStatButton.setPreferredSize(new Dimension(123,50));
		
		replayButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				frame.setVisible(false);
				frameList = new JFrame("FrameList");
		        frameList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frameList.setSize(370, 640);
		        frameList.setLocationRelativeTo(null);
				frameList.setVisible(true);
				
				String data[] = new File("./logs").list();
				System.out.println("data.len="+data.length);
				JList<String> list = new JList<>(data);
				list.setLayoutOrientation(JList.VERTICAL);
		        list.setVisibleRowCount(0);
		          
		        JScrollPane scroll = new JScrollPane(list);
		        scroll.setPreferredSize(new Dimension(250, 500));
		        frameList.add(scroll, BorderLayout.NORTH);
	            panel.add(scroll);
				
				JButton sortBestButton = new JButton("Sort Best");
				//sortBestButton.setPreferredSize(dim);
				sortBestButton.setFont(new Font(null, Font.BOLD,14));
				sortBestButton.setBackground(Color.GRAY);
				sortBestButton.setForeground(Color.LIGHT_GRAY);
				frameList.add(sortBestButton, BorderLayout.CENTER);
				panel.add(sortBestButton);
				
				JButton sortBadButton = new JButton("Sort Bad");
				//sortBadButton.setPreferredSize(dim);
				sortBadButton.setFont(new Font(null, Font.BOLD,14));
				sortBadButton.setBackground(Color.GRAY);
				sortBadButton.setForeground(Color.LIGHT_GRAY);
				frameList.add(sortBadButton, BorderLayout.SOUTH);
				panel.add(sortBadButton);
				
				JButton retButton = new JButton("<-Return");
				retButton.setPreferredSize(new Dimension(370,50));
				retButton.setFont(new Font(null, Font.BOLD,14));
				retButton.setBackground(Color.GRAY);
				retButton.setForeground(Color.LIGHT_GRAY);
				frameList.add(retButton, BorderLayout.LINE_END);
				
				retButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent event){
							frameList.dispose();
							frame.setVisible(true);
		                	if (frameTransLog != null && frameTransLog.isVisible())
		                		frameTransLog.dispose();
						}
				});
				panel.add(retButton);
				
				
		        MouseListener mouseListener = new MouseAdapter() {
		            public void mouseClicked(MouseEvent e) {
		                if (e.getClickCount() == 2){
		                  String replayLog = (String) list.getSelectedValue();
		                  
		  				framePlay = new JFrame("Replay");
				        framePlay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				        framePlay.setSize(370, 640);
				        framePlay.setLocationRelativeTo(null);
						framePlay.add(new RoadReplay(replayLog));
						framePlay.setVisible(true);
		                
						frameList.dispose();
		                }
		                else if(e.getClickCount() == 1){
		                	if (frameTransLog != null && frameTransLog.isVisible())
		                		frameTransLog.dispose();
		                	String replayLog = (String) list.getSelectedValue(); //получаем строку с именем log-a
		                	List<String> transLog = new ArrayList<String>();
                			transLog = getFileLog(replayLog);
                			
                			makeFrameForTransLog(transLog, replayLog);
		                }
		            }
		        };
						
				sortBestButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent event){
						DefaultListModel listModel = new DefaultListModel();
						Map<Integer, String> map = new HashMap<Integer, String>();
						List<Integer> masSize = new ArrayList<Integer>();
						
						long startTimeJava = System.currentTimeMillis();
						for(int i=0;i<data.length;i++){
							String[] retv = data[i].split("-", 0);
							if(retv.length>1){
								String[] retval = retv[1].split("\\.", 0);
								if(retval[0].equals("victory")){
									File curFile = new File(new String("logs/"+data[i]));
									int bytes = (int)curFile.length();
									map.put(bytes, data[i]); //in bytes
									 masSize.add(bytes);
								}
							}
						}
						long timeSpentJava = System.currentTimeMillis() - startTimeJava;
						
						long startTimeScala = System.currentTimeMillis();
						Sort sortScala = new Sort(map);
						sortScala.printMap();
						sortScala.sorting();
						long timeSpentScala = System.currentTimeMillis() - startTimeScala;
						System.out.println("Время Java: " + timeSpentJava + " мc"+"\nВремя Scala: "+ timeSpentScala+" мс");


						/**Сортировка*/
						Object mas[] = masSize.toArray();
						
						
						for(int i=0; i<mas.length;i++){
							for(int j=mas.length-1;j>i;j--){
								if(((Integer)mas[j-1]).intValue() > ((Integer)mas[j]).intValue()){
									int temp = ((Integer)mas[j-1]).intValue();
									mas[j-1] = mas[j];
									mas[j] = temp;
								}	
							}
						}
						
						for(int i=0; i<mas.length;i++){
							listModel.addElement(map.get(mas[i]));
						}
						list.setModel(listModel);

					}
					});

				
				sortBadButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent event){
						DefaultListModel listModel = new DefaultListModel();
						Map map = new HashMap<Integer, String>();
						List<Integer> masSize = new ArrayList<Integer>();
						
						long startTimeJava = System.currentTimeMillis();
						for(int i=0;i<data.length;i++){
							String[] retv = data[i].split("-", 0);
							if(retv.length>1){
								String[] retval = retv[1].split("\\.", 0);
								if(retval[0].equals("loss")){
									File curFile = new File(new String("logs/"+data[i]));
									int bytes = (int)curFile.length();
									map.put(bytes, data[i]); //in bytes
									 masSize.add(bytes);
								}
							}
						}
						long timeSpentJava = System.currentTimeMillis() - startTimeJava;
						
						long startTimeScala = System.currentTimeMillis();
						Sort sortScala = new Sort(map);
						sortScala.printMap();
						sortScala.sorting();
						long timeSpentScala = System.currentTimeMillis() - startTimeScala;
						System.out.println("Время Java: " + timeSpentJava + " мc"+"\nВремя Scala: "+ timeSpentScala+" мс");
						
						//sort
						Object mas[] = masSize.toArray();
						
						
						for(int i=0; i<mas.length;i++){
							for(int j=mas.length-1;j>i;j--){
								if(((Integer)mas[j-1]).intValue() > ((Integer)mas[j]).intValue()){
									int temp = ((Integer)mas[j-1]).intValue();
									mas[j-1] = mas[j];
									mas[j] = temp;
								}	
							}
						}
						
						for(int i=0; i<mas.length;i++){
							listModel.addElement(map.get(mas[i]));
						}
						list.setModel(listModel);
		        
		        
		       
		        
			}
		});
				
				list.addMouseListener(mouseListener);
				frameList.getContentPane().add(panel);
			}

		});
		
		botButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				frame.setVisible(false);
				framePlay = new JFrame("Robot");
		        framePlay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        framePlay.setSize(370, 640);
		        framePlay.setLocationRelativeTo(null);
				framePlay.add(new RoadBot());
				framePlay.setVisible(true);
				}
		});
		
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
					System.exit(0);
				}
		});
		
		getStatButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				frameStat = new JFrame("Statistics");
		        frameStat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        frameStat.setSize(300, 200);
	    		frameStat.setLocation(30,30);
	    		frameStat.setVisible(true);
	        	
	        	String nameList[] = new File("./logs").list();
	        	List<Integer> statList = new ArrayList<Integer>();
	        	int [] reStatList = new int[4];
	        	JList<String> listStat = new JList<>();
	        	String data[] = new String[5];
	        	
	        	
	        	for(int k=0; k<nameList.length;k++){
		        	statList = getStat(nameList[k]);		//получение статистики одной игры

	
		    		for(int jk=0;jk<statList.size();jk++)
		    			reStatList[jk] += statList.get(jk).intValue();		             
	        	}
	        	
	        	System.out.println("playingShot=" + playingShot);
	        	int playingTimeSec = (playingShot*30)/1000;
	        	int playingTimeMin = playingTimeSec/60;
	        	int playingTimeHour = playingTimeMin/60;
	        	playingTimeSec -= playingTimeMin*60;
	        	playingTimeMin -= playingTimeHour*60;
	        	
	    		data[0] = "Total count of acceleration: " + reStatList[0];
	    		data[1] = "Total count of deceleration: " + reStatList[1];
	    		data[2] = "Total count of direction to left: " + reStatList[2];
	    		data[3] = "Total count of direction to right: " + reStatList[3];
	    		data[4] = "Total playing time: "+playingTimeHour+"h "+playingTimeMin+"min "+playingTimeSec+"sec";
	    		
	    		listStat.setLayoutOrientation(JList.VERTICAL);
	            listStat.setVisibleRowCount(0);
	            listStat.setListData(data);
	        	
	    		
	            JScrollPane scroll = new JScrollPane(listStat);
	            scroll.setPreferredSize(new Dimension(250, 600));
	            frameStat.add(scroll, BorderLayout.NORTH);
			}
			
		});
		
		gameButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				
				frame.setVisible(false);
				framePlay = new JFrame("Play");
		        framePlay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        framePlay.setSize(370, 640);
		        framePlay.setLocationRelativeTo(null);
				framePlay.add(new RoadPlayer());
				framePlay.setVisible(true);
				
				framePlay.addKeyListener(new KeyListener(){ ////////////////////////not work
					public void keyPressed (KeyEvent event){
				        int key = event.getKeyCode();
				        if (key == KeyEvent.VK_ESCAPE){
				        	framePlay.dispose();
				        	frame.setVisible(true);
				        }
					}
					public void keyReleased(KeyEvent arg0) {
						System.out.println("ESCAPE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					}
					public void keyTyped(KeyEvent arg0) {
					}
				});
	
				}
		});
		
		this.setLayout(new BorderLayout());
		this.add(panel);
		this.add(gameButton, BorderLayout.NORTH);
		this.add(botButton, BorderLayout.CENTER);
		this.add(exitButton, BorderLayout.SOUTH);
		this.add(replayButton, BorderLayout.LINE_END);
		this.add(getStatButton, BorderLayout.WEST);
}

	public List<String> getFileLog(String readLog){
		BufferedReader in;
		String str;
		String path = "logs/" + readLog;
		File readLogFile = new File(path);
		List<String> state = new ArrayList<String>(); 
		
    	try{
    		in = new BufferedReader(new FileReader( readLogFile.getAbsoluteFile()));
    	}catch(IOException e) {
            throw new RuntimeException(e);
        	}
    	
    	try {
			while((str=in.readLine()) != null){
				String[] retval = str.split(" ", 0);
				if(retval.length == 3)
					state.add(str);	
			}
		} catch (IOException e1) {
			System.out.println("Error: read file.");
			e1.printStackTrace();
		}
    	
    	try{
    		in.close();
    	}catch(IOException e) {
            throw new RuntimeException(e);
        	}
    	playingShot += state.size();

    	
    	ReadableLogs readLogs = new ReadableLogs(state);
    	
    	return (readLogs.getTransLog());
    	
	}
		
 
	public List<Integer> getStat(String nameFile){
		
    	List<String> transLog = new ArrayList<String>();
		transLog = getFileLog(nameFile);
		GenerateStat genStat = new GenerateStat(transLog);
		List<Integer> stat = new ArrayList<Integer>();
		stat = genStat.getLogStat();
		
		return stat;
	}
	
	
	public void makeFrameForTransLog(List<String> transLog, String logFileName){
			frameTransLog = new JFrame("Translate log " + logFileName);
	        frameTransLog.setSize(370, 640);
	        frameTransLog.setLocation(30,30);
	        frameTransLog.setVisible(true);

	        
			String data[] = new String[transLog.size()];
			for(int k=0;k<transLog.size();k++)
				data[k] = transLog.get(k);
			JList<String> listLog = new JList<>(data);
			listLog.setLayoutOrientation(JList.VERTICAL);
	        listLog.setVisibleRowCount(0);
	          
	        JScrollPane scroll = new JScrollPane(listLog);
	        scroll.setPreferredSize(new Dimension(250, 600));
	        frameTransLog.add(scroll, BorderLayout.NORTH);
	}

	
    public void createGUI() {
        frame.setContentPane(new BgPanel());
        Container cont = frame.getContentPane();
   	createButtons();
   	example();
   	frame.setVisible(true);
    }
    

    public void paintWallpaper(Graphics g){
        Image img = new ImageIcon ("resource/wallpaper.jpg").getImage();;
        g.drawImage(img, 0, 0, null); 
    }
	    

    public void example() {
    	Font font = new Font("verdana",Font.BOLD ,15);
    	
        JPanel panel = new JPanel(new GridLayout(0,1));
        panel.setOpaque(false);
        setFont(font);
        panel.setForeground(Color.LIGHT_GRAY);
        EmptyBorder border = new EmptyBorder(20,10,10,10);
        BorderFactory.createTitledBorder(border, "Levels", TitledBorder.LEFT, TitledBorder.BOTTOM, font, Color.WHITE);
        panel.setBorder(border);
        ButtonGroup group = new ButtonGroup();
        
        AbstractButton abstract1 = new JRadioButton("Easy");
        abstract1.setFont(font);
        abstract1.setForeground(Color.LIGHT_GRAY);
        abstract1.setOpaque(false);
        panel.add(abstract1);
        group.add(abstract1);
        AbstractButton abstract2 = new JRadioButton("Medium");
        abstract2.setFont(font);
        abstract2.setForeground(Color.LIGHT_GRAY);
        abstract2.setOpaque(false);
        panel.add(abstract2);
        group.add(abstract2);
        AbstractButton abstract3 = new JRadioButton("Hard");
        abstract3.setFont(font);
        abstract3.setForeground(Color.LIGHT_GRAY);
        abstract3.setOpaque(false);
        panel.add(abstract3);
        group.add(abstract3);
        group.setSelected(abstract1.getModel(), true);
        frame.add(panel, BorderLayout.CENTER);
        
    }
    
}


class BgPanel extends JPanel{
    public void paintComponent(Graphics g){
        Image img = null;
        try {
            img = ImageIO.read(new File("resource/wallpaper.jpg"));
        } catch (IOException e) {}
        g.drawImage(img, 0, 0, null); 
    }
}




