/*
 * Areege Chaudhary
 * Student #: 10197607
 * 
 * This program is the Game of Pig. It is initialized through the main function and then
 * calls on the scoreHandler() method that adds up score totals and organizes the order of turns between
 * the player and the computer. In that method, it also calls on the method turnHandler(). This method
 * initiates the diceRoll() method (that generates random integers for the dice rolls)
 *  and traverses through the procedure of an individual turn. 
 */
import java.util.*;
public class Assn1_15atc1 {

/* 
 * This method welcomes the player and calls the scoreHandler() method that 
 * launches the rest of the program and begins the game.
 */
	public static void main(String[] args) {
		System.out.println("Welcome to the game of Pig!");
		System.out.println("The player rolls first!");
		scoreHandler(); 
	} //end of main method
  
/* 	
 * This method creates a 2 element array with randomly generated integer values
 * between 1 and 6. It then returns the array named roll.
 */	
	public static int[] diceRoll() {
		Random rand = new Random();
		int[] roll = {(rand.nextInt(6) + 1), (rand.nextInt(6) + 1)};
		return roll;
	} // end of diceRoll method
	
/* 	
 * This method handles the scores for the entire game. It organizes the turns between the player and the 
 * computer by calling the turnHandler method. It then uses the values returned by that method to 
 * add up an accumulated score. If either the player's or the computer's accumulated score reaches 100,
 * it prints a winning statement.  
 */	
	public static void scoreHandler() {
		int playerScore = 0;
		int computerScore = 0;
		int totalPlayerScore = 0;
		int totalComputerScore = 0;

		while ((totalPlayerScore < 100) && (totalComputerScore < 100)) {
			playerScore = turnHandler("Player", totalComputerScore); //obtains the turnScore
			//if the returned turn score evaluates to -1, it is the special case and sets the total score to 0
			if (playerScore == -1)
				totalPlayerScore = 0;
			//add the turn score to the player's total score
			else
				totalPlayerScore += playerScore;
			System.out.println("\nPlayer's total score is: " + totalPlayerScore + 
								", Computer's total score is: " + totalComputerScore);
			computerScore = turnHandler("Computer", totalComputerScore);
			if (computerScore == -1)
				totalComputerScore = 0;
			else 
				totalComputerScore += computerScore; 
			System.out.println("\nPlayer's total score is: " + totalPlayerScore + 
					", Computer's total score is: " + totalComputerScore);
		}//end of while loop
		//if total score reaches 100, winning statements are printed
		if (totalPlayerScore >= 100)
			System.out.println("Congratulations! You win! :)");
		if (totalComputerScore >= 100)
			System.out.println("Sorry! You lose! Computer wins, again :P");			
	}// end of scoreHandler() 

/* 	
 *  This method takes the current player (Player (human) or Computer) and the totalComputerScore as
 *  parameters. It then creates a while loop to encompass the executions that take place for one turn.
 *  It calls on the method diceRoll() to generate an array with random integers and then uses those
 *  values to determine the next block of code to execute. Depending on whether the player inputs
 *  "y" or "n", the program either exits the while loop and moves on to the next player's turn or restarts
 *  the while loop. At the end, it returns an integer: turnScore.
 */		
	public static int turnHandler(String player, int totalComputerScore) {
		int turnScore = 0;
		int[] dice = new int[2];
		int rollScore = 0;
		int computerScore = totalComputerScore;
		boolean turnOver = false;

		//while the boolean  variable turnOver is false, continue looping
		while (!turnOver) {
			dice = diceRoll();
			rollScore = (dice[0] + dice[1]);
			
			System.out.println("\n" + player + " rolled a " + dice[0] + " and a " + dice[1] + ".");
			//if both dice rolls are one's, turnScore is set to a special case (-1)
			if ((dice[0] == 1) && (dice[1] == 1)) {
				System.out.println("TURN OVER! " + player + "'s accumulated score is now 0!");
				turnScore = -1;
				turnOver = true; //loop is exited once this condition is met
			}//end of if two 1's block
			else if ((dice[0] == 1) || (dice[1] == 1)) {
				System.out.println("TURN OVER!" + player + "'s turn score is now 0!");
				turnScore = 0;
				turnOver = true;
			} //end of if one 1 block
			else if (dice[0] == dice[1]) {
				System.out.println(player + " rolls again!");
				continue;
			} //end of if matching/doubles block
			else { //if none of the cases above are met, add up the turn score
				turnScore = (turnScore + rollScore);
				System.out.println("Turn score is now " + turnScore + "!");
				//if it's the player's turn, call the player input method
				if (player == "Player") {
					String playerChoice = playerInput();
					//if the returned string is n, exit loop. If not, restart loop
					if (playerChoice.equals("n"))
						turnOver = true;
					else
						continue;
				} // end of if 'player' block
				else if (player == "Computer") {
					String computerChoice = computerStrategy(turnScore, computerScore);
					if (computerChoice.equals("n"))
						turnOver = true;
					else
						continue;
				}//end of else if 'computer' block
			} // end of else block (if none of the cases are met)
		} // end of while loop
		if (turnScore != -1) //if turnScore does not evaluate to the special case, print the following:
			System.out.println("The final turn score is " + turnScore + ".");
		return turnScore;
	} // end player roll method
	
/*
 * This method takes the turnScore and accumulated score as parameters and returns strings that 
 * determine whether or not to roll again in the turnHandler method.
 */
	public static String computerStrategy(int turnScore, int totalComputerScore) {
		if (turnScore >= 40) 
			return "n";
		else if ((turnScore + totalComputerScore) >= 100)
			return "n";	
		else
			return "y";
	} //end computerStrategy method

/*
 * This method asks the user for input (using Scanner) and returns that input in the form of a string.
 */
	public static String playerInput() {
		Scanner playerInput = new Scanner(System.in);
		String playerChoice = null;
		String dump = null;
		boolean inputOK = false; 
		
		System.out.println("Roll again? Enter 'y' or 'n': ");
		while (!inputOK) { //loop repeats if proper input is not obtained
			try {
				playerChoice = playerInput.nextLine();
				inputOK = true;
			} //end of try block
			catch (InputMismatchException e) { //if exception is reached, execute following a restart loop
				dump = playerInput.nextLine();
				System.out.println(dump + " is not a legal input. Please try again! :(");
			} //end of catch block
		} //end of while loop
		return playerChoice;
	} //end of playerInput method
	
} //end of Assn1_15atc1 class
