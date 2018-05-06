import java.io.IOException;
import java.util.Scanner;

public class Game {
	static int players = 0;
	static boolean gameOver = false;
	static String input;
	
	public static void main(String[] args) throws InterruptedException, IOException {
		int gameWinner = -1;
		Scanner scanner = new Scanner( System.in );
		
		//Get number of players (with loop for errors)
		while(players == 0) {
			System.out.print("This game is meant for 2-4 players. How many are playing? (Press 1 to exit).:  ");
			input = scanner.nextLine();
			try{
				players = Integer.parseInt(input);
			}catch(Exception e) {
				System.out.println("Sorry, that is an invalid choice. Please enter either 2, 3, or 4.");
			}
			if(players < 0 || players > 4) {
				players = 0;
				System.out.println("Sorry, that is an invalid choice.");
			}
		}
		
		//Exit if exit was chosen.
		if(players == 1) {
			System.out.println("Goodbye!");
			scanner.close();
			return;
		}
		//otherwise clear screen to start game.
		else {
			final String os = System.getProperty("os.name");
	        if (os.contains("Windows"))
	            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	        else
	        		System.out.print("\033[H\033[2J");
		}
		
		//declare arrays based on number of players.
		String playerNames[] = new String[players];
		int playerScores[] = new int[players];
		Card[] playerCards = new Card[players];
		
		//Print out the game rules
		System.out.println(
				"Welcome to the game, here's how it works.  For every round \n"
				+ "each player will draw a card. The person with the highest card\n"
				+ "will win the round and earn two points. \n\n"
				+ "Highest card is determined by face value, then by suit. There are\n"
				+ "also four penalty cards.  Penalty cards will cost you one point.\n"
				+ "The first player to reach 21 points wins, as long as they are at \n"
				+ "least 2 points ahead.  If a player makes it 21, but there is another\n"
				+ "player with 20 points, then we keep going until someone has scored\n"
				+ "two points higher than every other player.\n\n"
				+ "Now, let's start by getting your names to help keep track of rounds.");
		
		
		//Enter player names
		int i = 0;
		while(i < players) {
			System.out.print("\nPlayer " + (i+1) + ", please enter your name: ");
			
			playerNames[i] = scanner.nextLine();
			
			//as long as name is valid, continue on to next.
			if(checkValidName(playerNames, i)) {
				i++;
			}
		}
		
		System.out.println("\nGreat. When you're ready, let's get started! (Press enter to continue)\n");
		scanner.nextLine();

		//Clear screen
		final String os = System.getProperty("os.name");
        if (os.contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
        		System.out.print("\033[H\033[2J");
		
		//The actual game
		int round = 1;
		while(!gameOver) {
			System.out.println("Round "+ round + "!");
			i = 0;
			//Draw Cards
			while(i < players) {
				System.out.print("\n" + playerNames[i] + ", it's your turn.  Press enter key to draw a card");
				scanner.nextLine();
				//set match to true so we can enter loop the first time
				boolean match = true;
				while(match) {
					match = false; //set to false, will set to true if card matches later.
					
					//draw a card.
					playerCards[i] = new Card();
				
					int j = 0;
					
					//Make sure card wasn't already dealt, if it was loop again
					while(j < i) {
						if(playerCards[i].compareTo(playerCards[j]) == 0) {
							match = true;  //card already dealt, loop through again to get new card.
							break;
						}
						j++;
					}
				
					//if the card dealt was not matched, then print out the card and move on to next player.
					if(!match){
						System.out.println(playerCards[i].toString());
						if(playerCards[i].isPenalty()) {
							if(playerScores[i] > 0) {
								playerScores[i]--;
								System.out.println("Sorry, " + playerNames[i] + ", you just lost one point.");
							}else {
								System.out.println("You recieved a penalty card, but you don't current have any\npoints, so we'll let this one slide.");
							}
						}
						i++;
					}
				}
			}
			
			//find the winner of this round.  Increment score and announce.
			int winner = findRoundWinner(playerCards);
			playerScores[winner] += 2;
			System.out.println("\n" + playerNames[winner] + " wins 2 points!");

			//print scoreboard
			System.out.println("\nSCOREBOARD:");
			i = 0;
			while(i < players) {
				System.out.println(playerNames[i] + ":  " + playerScores[i]);
				i++;
			}
			System.out.println("\nPress enter to continue.");
			
			scanner.nextLine();
			//System.out.print("\033[H\033[2J"); //clears screens on Mac & linux.


	        if (os.contains("Windows")) {
	            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
	        }
	        else {
	        		System.out.print("\033[H\033[2J"); //clears screens on Mac & linux.
	        }
			//Check to see if someone won the game.
			gameWinner = checkForWinner(playerScores); 
			
			if(gameOver) {
				System.out.println("Congratulations, " + playerNames[gameWinner] + ", you won!");
			}
			round++; //move forward one round.
		}
		scanner.close();
		System.out.println("\n\n  GAME OVER \n\n");
		return;
	}

	private static boolean checkValidName(String[] playerNames, int currPlayer) {
		//check for empty name 
		int j = 0;
		
		if(playerNames[currPlayer].isEmpty() ){
			System.out.println("Please enter a unique name at least one character in length.");
			return false;
		}

		//check if name already exists
		while(j < currPlayer) {
			if(playerNames[j].equals(playerNames[currPlayer])) {
				System.out.println("Sorry, that name has been taken.  Please enter a unique name.");
				return false;
			}
				j++;
		}
		return true;
	}

	private static int checkForWinner(int[] playerScores) {
		// TODO Auto-generated method stub
		int topScore = 0;
		int winner = -1;
		
		//is anyone above 21?
		for(int i=0; i < players; i++) {
			if(playerScores[i] >=21) {
				if(playerScores[i] > topScore) {
					winner = i;
					topScore = playerScores[i];
				}
			}
		}
		
		//if so, find max
		if(topScore > 0) {
			for(int i=0; i<players; i++) {
				if(i != winner) {
					if(playerScores[winner] - playerScores[i] < 2) {
						return -1;
					}
				}
			}
			
			gameOver = true;
			return winner;
		}
		
		return -1;
	}

	private static int findRoundWinner(Card[] playerCards) {
		// TODO Auto-generated method stub
		int winner;
		int i = winner = 0;
		while(i < (players - 1)) {
			//only change if current winner loses.
			if(playerCards[winner].compareTo(playerCards[i+1]) == -1) {
				winner = i + 1;
			}
			i++;
		}
		return winner;
	}

}
