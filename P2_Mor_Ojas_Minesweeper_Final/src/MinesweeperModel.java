import java.util.Random;

public class MinesweeperModel {
	int grid[][];
	int origGrid[][];
	boolean revealGrid[][];
	boolean hasGameEnded = false;
	boolean hasGameLost = false;
	
	
//	public void play(P2_Mor_Ojas_MinesweeperModel ms) {
//		P2_Mor_Ojas_Controller c = new P2_Mor_Ojas_Controller();
//		c.setModel(ms);
//	    c.play();			
//	}
	
	void updateFlag(int row, int col) {
		
		if (isFlagged(row, col)) {
			unFlag(row,col);
		} else {
			setFlag(row,col);
		}
	}

	public void initialize(int row, int col, int numMines) {
		grid = new int[row][col];
		origGrid = new int[row][col];
		revealGrid = new boolean[row][col];
		Random rand = new Random();
		while (NumBombsRemaining() != numMines) {
			int x = rand.nextInt(row );
			int y = rand.nextInt(col );
			grid[x][y] = 9;
			origGrid[x][y] = grid[x][y];
		}
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] != 9) {
					grid[i][j] = calcValue(i, j);
					origGrid[i][j] = grid[i][j];
				}
				revealGrid[i][j] = false;
			}
		}
	}

	public boolean isBomb(int row, int col) {
		return (grid[row][col] == 9);
	}

	public int getRows() {
		return grid.length;
	}

	public int getCols() {
		return grid[0].length;
	}

	public int NumBombsRemaining() {
		int count = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 9) {
					count++;
				}
			}
		}
		return count;
	}

	public boolean isFlagged(int row, int col) {
		return (grid[row][col] == 10);
	}
	
	public void setFlag(int row, int col) {
		grid[row][col] = 10;
		revealGrid[row][col] = true;

	}

	public void unFlag(int row, int col) {
		grid[row][col] = origGrid[row][col];
		revealGrid[row][col] = false;
	}

	public int calcValue(int row, int col) {
		int counter = 0;
    	for (int i = row - 1; i <= row + 1; i++) {
    		for (int j = col - 1; j <= col + 1; j++) {
    			if (!(i == row && j == col) && i >= 0 && i < grid.length  &&
    			j >= 0 && j < origGrid[0].length && origGrid[i][j] == 9) {
    				counter++;
    			}
    		}
    	}
    	return counter;
	}
	
	public boolean reveal(int row, int col) {
		if (grid[row][col] == 9) {
			//GAME OVER HEREEEEEEE
			// you loose
			// reveal everything
			revealBombs();
			hasGameEnded = true;
			hasGameLost = true;
			System.out.println("You loose!!");
			return false;
		} else if (grid[row][col] >= 1 && grid[row][col] <= 8) {
			revealGrid[row][col] = true;
		} else {
			checkForZeros(row, col);			
		}
		if (isGameOver()) {
			System.out.println("You win!!");
			hasGameEnded = true;
			return false;
		}
		return true;
	}
	

	private void revealBombs() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 9) {
					revealGrid[i][j] = true;
				}
			}
		}
		
	}

	public boolean isOutSideGrid(int row, int col) {
		if (row < 0 ||  row >= grid.length || col < 0 || col >= grid[0].length) {
			return true;
		} else {
			return false;
		}
	}

	public void checkForZeros(int posX, int posY) {
		int[] xA = new int[] {  0,  1, 1, 1, 0, -1, -1, -1 };
		int[] yA = new int[] { -1, -1, 0, 1, 1,  1,  0, -1 };
		int BCount = 0;

		if (isOutSideGrid(posX, posY)) {
			return;
		}

				
		if (9 == grid[posX][posY]) {
			return;
		}
		
		if (revealGrid[posX][posY] == true) {
			return;
		}			
		
		for (int i = 0; i < 8; i++) {
			if (!isOutSideGrid(posX + xA[i], posY + yA[i]) && 9 == grid[posX + xA[i]][posY + yA[i]] ) {
				BCount++;
			}
		}

		revealGrid[posX][posY] = true;	
		
		if (BCount == 0) {
			for (int i = 0; i < 8; i++) {
				checkForZeros(posX + xA[i], posY + yA[i]);
			}
		}
		
		return;
	}
	
	public boolean isGameOver() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] != 9 && revealGrid[i][j] == false) {
					return false;
				}
			}
		}
		return true;
	}
	
	public int numFlags() {
		int counter = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 10) {
					counter++;
				}
			}
		}
		return counter;
	}
	
	public int numMines() {
		int counter = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (origGrid[i][j] == 9) {
					counter++;
				}
			}
		}
		return counter;
	}

	public int rowForYPos(double y) {
		return (int) (y / 32);
	}

	public int colForXPos(double x) {
		return (int) (x / 32);
	}
	
	

}
