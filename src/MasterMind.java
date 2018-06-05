 import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * @author James Zhan
 * 
 * 
 */
public class MasterMind {
	protected String[] tokenColors; // stores all the different colors
	protected int positions; // number of positions
	protected ArrayList<String []> list = new ArrayList<>(); // storing all combinations inside a Arraylist of string arrays
	
	public MasterMind() {
	
	}
	
	public MasterMind(String[] tokenColors, int positions) { 
		this.tokenColors = tokenColors;
		this.positions = positions;
		
	}
	// creates every different combination of codes
	public void createCombinations(String[] possibilities, int positions, int index) { 
		if (index == positions) {
			String[] clone = new String[positions];  // makes a deep copy of the possibilities array 
			for (int i = 0; i < positions; i ++) {
				clone[i] = possibilities[i];
			}
			list.add(clone);
		} else {
			for (int i = 0; i < tokenColors.length; i ++) { // for loop recursion
				possibilities[index] = tokenColors[i];
				createCombinations(possibilities, positions, index+1);
			}
		}
	}
	protected Random random = new Random();
	
	public String[] nextMoveClone; 
 
	// outputs the computer's next guess through random choice
	public String[] nextMove(ArrayList<String[]> list) {
		int guess = random.nextInt(list.size()); 
		nextMoveClone = new String[positions]; 
		for (int i = 0; i < positions; i ++) {
			nextMoveClone[i] = list.get(guess)[i];
		}
		return list.get(guess);
	}
	
	// outputs the number of correct positions and incorrect positions for a given code 
	public int[] getData(String[] possibilities, String[] compare) { 
		int black = 0;
		String[] guess = new String[positions];
		String[] temp = new String[positions];
		for (int i = 0; i < positions; i ++) { // copy of possibilities array and compare array because later they are modified
			guess[i] = compare[i];
			temp[i] = possibilities[i];
		}
		for (int i = 0; i < positions; i ++) { // calculates number of blacks (correct positions)
			if (temp[i] == guess[i]) {
				black ++;
				temp[i] = "blacked" + i; // can't count the black pegs as white pegs
				guess[i] = "blacked" + i;
			}
		}
		
		int white = 0;
		for (int i = 0; i < positions; i ++) { // calculates number of whites (incorrect positions but correct color)
			for (int j = i+1; j < positions; j ++) {
				if (temp[j] == guess[i]) {
					white ++;
					temp[j] = "whited"; 
					break; //it moves on to the next i value, since you can't count more than one white for one peg
				}
			}
		}
		int[] response = {black, white}; // returns the int array of blacks, whites
		return response;
	}
	
	// removes those codes that do not correspond to the clues given 
	public void response(int black, int white) { 
		int[] convert = new int[2];
		ArrayList<String[]> copy = new ArrayList<>(list);  
		for (int i = 0; i < list.size(); i ++) { 
			convert = getData(list.get(i), nextMoveClone);
			if ((convert[0] != black) || (convert[1] != white)) { // if the codes aren't consistent with the clue given to the computer's guess, then remove it
				copy.remove(list.get(i));
			} 
		}
		list = new ArrayList<>(copy);
	}
	
	
	public void newGame() {
		System.out.print("Do you want to play again? Enter yes or no ");
		String answer = scanner.next();
		if (answer.equals("yes") || answer.equals("y")) {
			System.out.println("Welcome Back! ");
			gameStart();
		} else {
			System.out.println("Thanks for playing MasterMind! ");
		}
	}
	
	protected Scanner scanner = new Scanner(System.in);
	
	public void gameStart() {
		System.out.print("Enter the number of positions you want to play with: "); // asks for positions, number of colors, which colors
		int numPosition = scanner.nextInt(); 
		System.out.print("Enter the number of colors you want to play with: ");
		int numColors = scanner.nextInt(); 
		String[] allColors = new String[numColors];
		System.out.println("Now enter the different types of colors you want to play with: ");
		for (int i = 0; i < numColors; i ++) {
			System.out.print("Enter color #" + (i+1) + ": ");
			String color = scanner.next();
			allColors[i] = color;
		}
		System.out.println("Now come up with a secret code in your mind. Once you have done so, press enter to continue. ");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		MasterMind game1 = new MasterMind(allColors, numPosition);
		String[] possibilities = new String[game1.positions];
		game1.createCombinations(possibilities, game1.positions, 0);
		int guess = 0;
		
		while (game1.list.size() != 1) { // iterates over the game until there is one array left in list
			guess ++;
			System.out.println("Guess number: " + guess);
			System.out.println("This is the computer's guess: " + Arrays.toString(game1.nextMove(game1.list)));
			int black = 0;
			int white = 0;
			ArrayList<String[]> copy = new ArrayList<>(game1.list);
			// checks for input that makes no code agreeable with its clues
			do {
				game1.list = new ArrayList<>(copy);
				System.out.print("Enter the number of correct positions between your code and the computer's guess: ");
				black = scanner.nextInt();
				System.out.print("Enter the number of incorrect positions but correct color between your code and the computer's guess: ");
				white = scanner.nextInt();
				game1.response(black, white);
				if (game1.list.size() == 0) {
					System.out.println("Invalid input... please try again. ");
				}
				
			} while(game1.list.size() == 0);
		}
		System.out.println("The correct array is " + Arrays.toString(game1.list.get(0)));
		game1.newGame();
		scanner.close();
	}
	
	public static void main(String[] args) {
		System.out.println("Welcome to MasterMind! ");
		MasterMind start = new MasterMind();
		start.gameStart();
	}
}
