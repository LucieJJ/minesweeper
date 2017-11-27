package worksheet7;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameController {
	@SuppressWarnings("resource")
	public void selectLevel() {
		System.out.println("------------------------------------");
		System.out.println("Please select a level:");
		System.out.println("0---Easy");
		System.out.println("1---Medium");
		System.out.println("2---Hard");

		boolean inputerror = true;
		Scanner sc;
		do {
			sc = new Scanner(System.in);
			try {
				level = sc.nextInt();
				if (level < 0 || level > 2)
					inputerror = true;
				else
					inputerror = false;
			} catch (InputMismatchException ime) {
				inputerror = true;
			}
			if (inputerror)
				System.out.println("input 0,1,2!");
		} while (inputerror);
	}

	public void startGame() {
		ms = new MineSweeper(level);
		ms.printGrid();

		boolean gameover = false;
		while (!gameover) {
			gr = new GuessReader();
			int status = ms.checkGuess(gr.getRow(), gr.getCol());
			ms.printGrid();
			if (status == 1) {
				System.out.println("You Win");
				gameover = true;
			} else if (status == 0) {
				System.out.println("Game Over");
				gameover = true;
			}
		}
		if (continuePlay()) {
			selectLevel();
			startGame();
		} else {
			System.out.println("Bye!");
		}
	}

	@SuppressWarnings("resource")
	private boolean continuePlay() {
		System.out.println("Plat again?:");
		System.out.println("0---Yes");
		System.out.println("1---No");

		boolean inputerror = true;
		Scanner sc;
		int con = 0;
		do {
			sc = new Scanner(System.in);
			try {
				con = sc.nextInt();
				if (con < 0 || con > 1)
					inputerror = true;
				else
					inputerror = false;
			} catch (InputMismatchException ime) {
				inputerror = true;
			}
			if (inputerror)
				System.out.println("input 0,1!");
		} while (inputerror);
		if (con == 0) {
			return true;
		} else {
			return false;
		}
	}

	int level = -1;// 0 1 2
	private MineSweeper ms;
	private GuessReader gr;
}//I learnt that from TA, I will try study harder T_T
