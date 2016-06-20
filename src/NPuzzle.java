import java.util.Random;
import java.util.Scanner;

public class NPuzzle {

	static long start = System.currentTimeMillis();
	long elapsedTimeMillis = 0;

	private GameState state = GameState.PLAYING;

	private int sirka, dlzka;

	public NPuzzle(int sirka, int dlzka) {
		this.dlzka = dlzka;
		this.sirka = sirka;
	}

	public NPuzzle() {
		this(4,4);
	}

	public int getSirka() {
		return sirka;
	}

	public int getDlzka() {
		return dlzka;
	}

	public GameState getState() {
		return state;
	}

	public int[] generate(int[] mojePole) {

		for (int i = 0; i < getDlzka() * getSirka(); i++) {
			mojePole[i] = i + 1;
			mojePole[(getDlzka() * getSirka()) - 1] = 0;
		}
		shuffleArray(mojePole);
		return mojePole;
	}

	public int[][] createPuzzle(int[][] moje2Pole, int[] mojePole) {

		int iteration = 0;
		for (int x = 0; x < getSirka(); x++) {
			for (int y = 0; y < getDlzka(); y++) {
				moje2Pole[x][y] = mojePole[iteration];
				iteration++;
			}
		}
		return moje2Pole;
	}

	public void printNPuzzle(int[][] moje2Pole) {

		for (int x = 0; x < getSirka(); x++) {
			for (int y = 0; y < getDlzka(); y++) {
				if (moje2Pole[x][y] < 10) {
					System.out.print(" " + moje2Pole[x][y] + "  ");
				} else {
					System.out.print(moje2Pole[x][y] + "  ");
				}
				if (y==getDlzka()-1) {
					System.out.println();
				}
			}
		}
	}

	private int[] shuffleArray(int[] mojePole) {

		int index, temp;
		Random random = new Random();
		for (int i = mojePole.length - 1; i > 0; i--) {
			index = random.nextInt(i + 1);
			temp = mojePole[index];
			mojePole[index] = mojePole[i];
			mojePole[i] = temp;
		}
		return mojePole;
	}

	private int[][] update(int[][] moje2Pole) {
		getStartTime();
		printNPuzzle(moje2Pole);
		Scanner userInput = new Scanner(System.in);
		System.out.println("Enter 'UP' or 'DOWN' or 'LEFT' or 'RIGHT' .");
		System.out.println("Or if you are lazy... Enter 'W' or 'A' or 'S' or 'D' for movement.");
		System.out.println("Or if you are really really really lazy... Enter 'exit'.");
		System.out.println("Enter 'new' if you want to start a new game.");
		String input = userInput.next();
		input = input.toUpperCase();
		
		try{
			input = input.toUpperCase();
		} catch (NumberFormatException e){
			//It never happens
		}
		
		for (int x = 0; x < 4; x++) {
			boolean change = false;
			for (int y = 0; y < 4; y++) {

				if (moje2Pole[x][y] == 0) {

					if (input.equals("W") || input.equals("UP")) {
						if (x == getSirka() - 1) {
							System.out.println("Cant move there. Try again.");
							break;
						}
						moje2Pole[x][y] = moje2Pole[x + 1][y];
						moje2Pole[x + 1][y] = 0;
						change = true;
						break;
					}

					if (input.equals("S") || input.equals("DOWN")) {
						if (x == 0) {
							System.out.println("Cant move there.");
							break;
						}
						moje2Pole[x][y] = moje2Pole[x - 1][y];
						moje2Pole[x - 1][y] = 0;
						change = true;
						break;
					}

					if (input.equals("A") || input.equals("LEFT")) {
						if (y == getDlzka() - 1) {
							System.out.println("Cant move there.");
							break;
						}
						moje2Pole[x][y] = moje2Pole[x][y + 1];
						moje2Pole[x][y + 1] = 0;
						change = true;
						break;
					}

					if (input.equals("D") || input.equals("RIGHT")) {
						if (y == 0) {
							System.out.println("Cant move there.");
							break;
						}
						moje2Pole[x][y] = moje2Pole[x][y - 1];
						moje2Pole[x][y - 1] = 0;
						change = true;
						break;
					}
					if (input.equals("EXIT")) {
						System.exit(0);
					}
					if (input.equals("NEW")) {
						StartGame();
					}
				}
			}
			if (change) {
				break;
			}
		}
		return moje2Pole;
	}

	public boolean isSorted(int[][] moje2Pole) {

		boolean help = false;
		for (int i = 0; i < moje2Pole.length; i++) {
			for (int j = 0; j < moje2Pole.length; j++) {
				if (moje2Pole[i][j] < moje2Pole[i][j + 1]) {
					help = true;
				} else {
					if (moje2Pole[getDlzka()][getSirka()] == 0 && help == true) {
						help = true;
					} else {
						help = false;
					}
				}
			}
		}
		return help;
	}

	public void Game(int[][] moje2Pole) {

		do {
			update(moje2Pole);
			if (getState() == GameState.SOLVED) {
				System.out.println("CONGRATULATIONS! YOU WON THE GAME!");

				System.exit(0);
			}
		} while (true);
	}

	private static void StartGame() {

		NPuzzle generate = new NPuzzle();

		int[] mojePole = new int[generate.getDlzka() * generate.getSirka()];
		int[][] moje2Pole = new int[generate.getDlzka()][generate.getSirka()];

		System.out.println("Game started!");
		System.out.println("Clue: Character '0' represents blank space.");
		generate.generate(mojePole);
		NPuzzle generate1 = new NPuzzle();
		generate1.createPuzzle(moje2Pole, mojePole);
		NPuzzle start = new NPuzzle();
		start.Game(moje2Pole);
	}

	public static long getStartTime() {

		long elapsedTimeMillis = (System.currentTimeMillis() - start) / 1000;
		System.out.println("Time: " + elapsedTimeMillis + " seconds.");
		return elapsedTimeMillis;
	}

	public static void main(String[] args) {
		StartGame();
	}

}
