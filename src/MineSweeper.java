package worksheet7;

import java.util.Random;

public class MineSweeper {

	public MineSweeper(int level) {
		if (level == 0) {
			row = 9;
			col = 9;
			bomb = 10;
		} else if (level == 1) {
			row = 16;
			col = 16;
			bomb = 40;
		} else if (level == 2) {
			row = 16;
			col = 30;
			bomb = 99;
		} else {
			System.out.println("Error when set level");
			System.exit(-1);
		}
		//I learnt that from TA
		int bombpos[] = randomArray(0, row * col - 1, bomb);
		initGrid(bombpos);
	}

	public void printGrid() {
		System.out.print(" \\ ");
		for (int c = 0; c < col; ++c)
			System.out.printf("%2d ", c);
		System.out.println(" / ");
		for (int r = 0; r < row; ++r) {
			System.out.printf("%2d|", r);
			for (int c = 0; c < col; ++c) {
				if (grid[r][c].mined) {
					if (grid[r][c].isbomb)
						System.out.printf("%2c ", '*');
					else {
						if (grid[r][c].clear)
							System.out.printf("   ");
						else
							System.out.printf("%2d ", grid[r][c].nbomb);
					}
				} else {
					System.out.print(" ? ");
				}
			}
			System.out.printf("|%2d", r);
			System.out.println();
		}
		System.out.print(" / ");
		for (int c = 0; c < col; ++c)
			System.out.printf("%2d ", c);
		System.out.println(" \\ ");
	}

	private int[] randomArray(int min, int max, int n) {
		int len = max - min + 1;

		if (max < min || n > len) {
			System.exit(-1);
		}

		int[] source = new int[len];
		for (int i = min; i < min + len; i++) {
			source[i - min] = i;
		}

		int[] result = new int[n];
		Random rd = new Random();
		int index = 0;
		for (int i = 0; i < result.length; i++) {
			index = Math.abs(rd.nextInt() % len--);
			result[i] = source[index];
			source[index] = source[len];
		}
		return result;
	}

	private void initGrid(int bombpos[]) {
		grid = new Cell[row][col];
		for (int r = 0; r < row; ++r) {
			for (int c = 0; c < col; ++c) {
				grid[r][c] = new Cell();
			}
		}
		for (int i = 0; i < bomb; ++i) {
			int index = bombpos[i];
			int rr = index / col;
			int cc = index % col;
			grid[rr][cc].isbomb = true;
		}
		for (int r = 0; r < row; ++r) {
			for (int c = 0; c < col; ++c) {
				int count = 0;
				if (r != 0)
					count += grid[r - 1][c].isbomb ? 1 : 0;
				if (r != row - 1)
					count += grid[r + 1][c].isbomb ? 1 : 0;
				if (c != 0)
					count += grid[r][c - 1].isbomb ? 1 : 0;
				if (c != col - 1)
					count += grid[r][c + 1].isbomb ? 1 : 0;
				if (r != 0 && c != 0)
					count += grid[r - 1][c - 1].isbomb ? 1 : 0;
				if (r != 0 && c != col - 1)
					count += grid[r - 1][c + 1].isbomb ? 1 : 0;
				if (r != row - 1 && c != 0)
					count += grid[r + 1][c - 1].isbomb ? 1 : 0;
				if (r != row - 1 && c != col - 1)
					count += grid[r + 1][c + 1].isbomb ? 1 : 0;
				grid[r][c].nbomb = count;
				if (count == 0)
					grid[r][c].clear = true;
				else
					grid[r][c].clear = false;
			}
		}
	}

	public int checkGuess(int x, int y) {
		if (x < 0 || x > col - 1 || y < 0 || y > col - 1) {
			return -1;
		}

		if (grid[x][y].isbomb) {
			for (int r = 0; r < row; ++r) {
				for (int c = 0; c < col; ++c) {
					grid[r][c].mined = true;
				}
			}
			return 0;
		}

		autoMine(x, y);
		int nmineds = 0;
		for (int r = 0; r < row; ++r) {
			for (int c = 0; c < col; ++c) {
				nmineds += grid[r][c].mined ? 1 : 0;
			}
		}
		if (col * row == nmineds + bomb)
			return 1;
		else
			return 2;
	}

	private void autoMine(int x, int y) {
		if (x < 0 || x > row || y < 0 || y > col)
			return;
		if (grid[x][y].mined)
			return;
		grid[x][y].mined = true;
		if (grid[x][y].clear) {
			if (x > 0) {
				autoMine(x - 1, y);
			}
			if (y > 0) {
				autoMine(x, y - 1);
			}
			if (x < row - 1) {
				autoMine(x + 1, y);
			}
			if (y < col - 1) {
				autoMine(x, y + 1);
			}
			if (x > 0 && y > 0) {
				autoMine(x - 1, y - 1);
			}
			if (x < row - 1 && y > 0) {
				autoMine(x + 1, y - 1);
			}
			if (x > 0 && y < col - 1) {
				autoMine(x - 1, y + 1);
			}
			if (x < row - 1 && y < col - 1) {
				autoMine(x + 1, y + 1);
			}
		}
	}

	private Cell grid[][];
	private int row = 0;
	private int col = 0;
	private int bomb = 0;

	private class Cell {
		boolean isbomb = false;
		int nbomb = 0;
		boolean clear = true;
		boolean mined = false;
	}
}
