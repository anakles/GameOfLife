import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	public static int SIZE;
	private static int MAX_GENERATIONS = 100;
	private static Cell[][] cells; 
	
	public GamePanel (int size) {
		this.setSize(new Dimension(size, size));
		this.setLayout(new BorderLayout());
		SIZE = size;
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//Toggle the cell at the mouses position:
				int x = e.getX();
				int y = e.getY();
				cells[x][y].toggleLife();
				System.out.println("Added a custom cell at ("+x+")("+y+")");
				
				updateUI();
			}
		});
		
		
		System.out.println("Creating cells...");
		createCells();
		
	}
	
	public void createCells() {
		cells = new Cell[SIZE][SIZE];
		//initializing the array with dead cells
		for(int x = 0; x < SIZE; x++) {
			for(int y = 0; y < SIZE; y++) {
				//with a given chance, the cell can be alive
				boolean isAlive = (Math.random() < 0.02);
				cells[x][y] = new Cell(x, y, isAlive);
			}
		}
	}
	
	public void startIterating() {
		updateUI();
		
		while(MainApplication.getRunning()) {
			//System.out.println("Calculating the new generation...");	
			
			//Cloning the currently displayed cells, so we can iterate over one generation without including the changes 
			ArrayList<Cell> tempCellList = new ArrayList<>();
			for(int x = 0; x < SIZE; x++) {
				for(int y = 0; y < SIZE; y++) {
					tempCellList.add(cells[x][y]);
				}
			}
		
			//Now iterating over all cells and apply the game rules:
			for(Cell c : tempCellList) {
				//Calculating the individual amount of neighbours
				int tempNeighbors = 0;
				try{
					tempNeighbors = GameRules.getAmountOfNeighbours(cells, c);
				}
				catch (ArrayIndexOutOfBoundsException e) {
					// TODO: handle exception
					e.printStackTrace();
					System.out.println("ERROR >>> This cell is at the border");
				}
				
				//Apply the game rules to the current cell
				GameRules.applyGameRules(c, tempNeighbors);
				
				//add the cell back into the panel
				cells[c.getX()][c.getY()] = c;
			}
				
			this.updateUI();
			
			//MAX_GENERATIONS--;
		}
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);

		for(int x = 0; x < SIZE; x++) {
			for(int y = 0; y < SIZE; y++){
				
				if(cells[x][y].isAlive())
					g.setColor(Color.WHITE);
				else 
					g.setColor(Color.BLACK);
				
				
				g.drawRect(x, y, 1, 1);
			}
		}
	
	
	
	}
	
	public void killAllCells() {
		for(int x = 0; x < SIZE; x++) {
			for(int y = 0; y < SIZE; y++){
				cells[x][y].kill();
			}
		}
	}
	
}
