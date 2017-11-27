package worksheet7;

import java.util.Scanner;
import java.util.InputMismatchException;

public class GuessReader {

	public GuessReader() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		System.out.println("input the position to mine(x y):");
		try {
			x = sc.nextInt();
			y = sc.nextInt();
		} catch (InputMismatchException ime) {
			System.out.println("inpute error!");
		}
	}

	public int getRow() {
		return x;
	}

	public int getCol() {
		return y;
	}

	private int x = -1;
	private int y = -1;
}//I learnt that from TA
