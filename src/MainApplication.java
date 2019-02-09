import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.naming.InitialContext;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.CellEditorListener;

public class MainApplication {

	private static int PANEL_SIZE = 800;
	private static JFrame frame;
	private static GamePanel gamePanel;
	private static JPanel buttonPanel;
	
	private static Thread uiThread;
	private static Thread gameThread;
	private static boolean running = false;
	
	public static void main(String[] args) {
		System.out.println("Application started...");
		
		//Creating separate threads for each interaction to increase the performance
		try{
			uiThread = new Thread(new Runnable() {
		
				@Override
				public void run() {
					initializePanel();
				}
			});
			uiThread.start();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR >> Could not load GamePanel");
		}
		
		System.out.println("> GamePanel initialized");
			
	}
	
	private static void initializePanel() {
		frame = new JFrame("Game of Life");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(PANEL_SIZE, PANEL_SIZE+100);
		frame.setLayout(new BorderLayout());
		
		gamePanel = new GamePanel(PANEL_SIZE);
		buttonPanel = initializeControls();
		
		frame.add(gamePanel, BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		frame.revalidate();
		frame.repaint();
		
		//System.out.println("Starting the iterations...");
		//gamePanel.startIterating();
	}
	
	private static JPanel initializeControls() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 0));
		
		JButton btn_start = new JButton("Start");
		btn_start.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				gameThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						changeRunning();
						gamePanel.startIterating();
					}
				});
				gameThread.start();
			}
		});
		
		buttonPanel.add(btn_start);
		
		JButton btn_clear = new JButton("Clear");
		btn_clear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//If a simulation is running, it will be stopped first 
				if(getRunning()) {
					 changeRunning();
				 }
				
				gamePanel.killAllCells();
				gamePanel.updateUI();
			}
		});
		buttonPanel.add(btn_clear);
		
		JLabel label_size = new JLabel("Size:");
		buttonPanel.add(label_size);
		JTextField txt_size = new JTextField();
		buttonPanel.add(txt_size);
		JButton btn_size = new JButton("Resize");
		btn_size.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
					int newSize = Integer.valueOf(txt_size.getText());
					PANEL_SIZE = newSize;
					frame.dispose();
					initializePanel();
					
				}
				catch (Exception e1) {
					System.out.println("ERROR >>> Invalid input for resize!");
				}
			}
		});
		buttonPanel.add(btn_size);
		
		return buttonPanel;
	
	}
	
	
	public static synchronized boolean getRunning() {
		return running;
	}
	
	public static synchronized void changeRunning() {
		running = !running;
	}
	
	
	

}
