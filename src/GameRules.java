import java.util.ArrayList;

public class GameRules {

	public static int getAmountOfNeighbours(Cell[][] cells, Cell cell){
		int neighbors = 0;
		int cell_x = cell.getX();
		int cell_y = cell.getY();
		
		
		//Creating a list with the 8 neighbor cells:
		//Reminder: Center-center would be the current cell itself
		ArrayList<Cell> neighborCells = new ArrayList<>();
		
		//Checking for the borders: cells at the border need special treatment:
		int borderType = getBorderType(cell);
		switch (borderType) {
		case 1:
			neighborCells.add(cells[cell_x    ][cell_y - 1]);		//Top-center
			neighborCells.add(cells[cell_x + 1][cell_y - 1]);		//Top-right
			neighborCells.add(cells[cell_x + 1][cell_y    ]);		//Center-Right
			neighborCells.add(cells[cell_x + 1][cell_y + 1]);		//Bottom-Right
			neighborCells.add(cells[cell_x    ][cell_y + 1]);		//Bottom-Center
			break;
			
		case 2:
			neighborCells.add(cells[cell_x - 1][cell_y - 1]);		//Top-left
			neighborCells.add(cells[cell_x    ][cell_y - 1]);		//Top-center
			neighborCells.add(cells[cell_x    ][cell_y + 1]);		//Bottom-Center
			neighborCells.add(cells[cell_x - 1][cell_y + 1]);		//Bottom-Left
			neighborCells.add(cells[cell_x - 1][cell_y    ]);		//Center-Left
			break;

		case 3:
			neighborCells.add(cells[cell_x + 1][cell_y    ]);		//Center-Right
			neighborCells.add(cells[cell_x + 1][cell_y + 1]);		//Bottom-Right
			neighborCells.add(cells[cell_x    ][cell_y + 1]);		//Bottom-Center
			neighborCells.add(cells[cell_x - 1][cell_y + 1]);		//Bottom-Left
			neighborCells.add(cells[cell_x - 1][cell_y    ]);		//Center-Left
			break;
					
		case 4:
			neighborCells.add(cells[cell_x - 1][cell_y - 1]);		//Top-left
			neighborCells.add(cells[cell_x    ][cell_y - 1]);		//Top-center
			neighborCells.add(cells[cell_x + 1][cell_y - 1]);		//Top-right
			neighborCells.add(cells[cell_x + 1][cell_y    ]);		//Center-Right
			neighborCells.add(cells[cell_x - 1][cell_y    ]);		//Center-Left
			break;
			
		case 5:
			neighborCells.add(cells[cell_x + 1][cell_y    ]);		//Center-Right
			neighborCells.add(cells[cell_x + 1][cell_y + 1]);		//Bottom-Right
			neighborCells.add(cells[cell_x    ][cell_y + 1]);		//Bottom-Center
			break;
			
		case 6:
			neighborCells.add(cells[cell_x    ][cell_y + 1]);		//Bottom-Center
			neighborCells.add(cells[cell_x - 1][cell_y + 1]);		//Bottom-Left
			neighborCells.add(cells[cell_x - 1][cell_y    ]);		//Center-Left
			break;
			
		case 7:
			neighborCells.add(cells[cell_x - 1][cell_y - 1]);		//Top-left
			neighborCells.add(cells[cell_x    ][cell_y - 1]);		//Top-center
			neighborCells.add(cells[cell_x - 1][cell_y    ]);		//Center-Left
			break;
			
		case 8:
			neighborCells.add(cells[cell_x    ][cell_y - 1]);		//Top-center
			neighborCells.add(cells[cell_x + 1][cell_y - 1]);		//Top-right
			neighborCells.add(cells[cell_x + 1][cell_y    ]);		//Center-Right
			break;
			
		default:
			neighborCells.add(cells[cell_x - 1][cell_y - 1]);		//Top-left
			neighborCells.add(cells[cell_x    ][cell_y - 1]);		//Top-center
			neighborCells.add(cells[cell_x + 1][cell_y - 1]);		//Top-right
			neighborCells.add(cells[cell_x + 1][cell_y    ]);		//Center-Right
			neighborCells.add(cells[cell_x + 1][cell_y + 1]);		//Bottom-Right
			neighborCells.add(cells[cell_x    ][cell_y + 1]);		//Bottom-Center
			neighborCells.add(cells[cell_x - 1][cell_y + 1]);		//Bottom-Left
			neighborCells.add(cells[cell_x - 1][cell_y    ]);		//Center-Left
			break;
		}
		
		
		for(Cell c : neighborCells) {
			if(c.isAlive())
				neighbors++;
		}		
		
		return neighbors;
	}
	
	/**Applies the game rules of Conways Game of Life to the given cell.
	 * Rules: 
	 * - A dead cell with exactly 3 living neighbors is revived
	 * - A living cell with less than 2 neighbors will die due to solitude
	 * - A living cell with 2-3 living neighbors will stay alive
	 * - A living cell with more than 3 neighbors will die due to over population 
	 */
	public static void applyGameRules(Cell cell, int neighbors) {
		if(!cell.isAlive() && neighbors == 3) {
			cell.revive();
		}
		else {
			switch(neighbors) {
				case 2:
					break;
				case 3:
					break;
				default: 
					cell.kill();
			}
		}	
	}	
	
	/**Returns to which border the cell is aligned
	 * 0 = no border
	 * 1 = left
	 * 2 = right
	 * 3 = top
	 * 4 = bottom
	 * 5 = 1 and 3
	 * 6 = 3 and 2
	 * 7 = 2 and 4
	 * 8 = 4 and 1 
	 */
	private static int getBorderType(Cell cell) {
		int x = cell.getX(), y = cell.getY();
		int borderType = 0;
		
		if(border_rule_1(x, y))
			borderType = 1;
		if(border_rule_2(x, y))
			borderType = 2;
		if(border_rule_3(x, y))
			borderType = 3;
		if(border_rule_4(x, y))
			borderType = 4;
		if(border_rule_5(x, y))
			borderType = 5;
		if(border_rule_6(x, y))
			borderType = 6;
		if(border_rule_7(x, y))
			borderType = 7;
		if(border_rule_8(x, y))
			borderType = 8;
		
		return borderType;
	}
	
	
	private static boolean border_rule_1(int x, int y) {
		return (x - 1 < 0);
	}

	private static boolean border_rule_2(int x, int y) {
		return (x + 1 >= GamePanel.SIZE);
	}
	
	private static boolean border_rule_3(int x, int y) {
		return (y - 1 < 0);
	}
	
	private static boolean border_rule_4(int x, int y) {
		return (y + 1 >= GamePanel.SIZE);
	}
	
	private static boolean border_rule_5(int x, int y) {
		return (border_rule_1(x, y) && border_rule_3(x, y));
	}
	
	private static boolean border_rule_6(int x, int y) {
		return (border_rule_3(x, y) && border_rule_2(x, y));
	}
	
	private static boolean border_rule_7(int x, int y) {
		return (border_rule_2(x, y) && border_rule_4(x, y));
	}
	
	private static boolean border_rule_8(int x, int y) {
		return (border_rule_4(x, y) && border_rule_1(x, y));
	}

}
