import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static boolean ISDEALER = true;
	static Scanner reader = new Scanner(System.in);
	static BlackjackDeck deck  = new BlackjackDeck();
	
	public static void main(String[]args){
		System.out.println("Hello and welcome to the Blackjack table!");
		System.out.println("Enter 1 for automatic algo testing mode, and 0 to play Blackjack manually");
		int mode = reader.nextInt();
		
		ArrayList<Player> playerList  = new ArrayList<Player>();
		//Num of players in game
		int numPlayers = 10;
		//Num of hands to play
		int numHands = 1000;
		
		if(mode == 0){
			numHands = -1;
			//Ask user how many players if manual
			System.out.println("How many players are there?");
			numPlayers = reader.nextInt();	
			
			//Manual fill in of num hands
			while(numHands <= 0){
				System.out.println("How many hands would you like to play?");
				numHands = reader.nextInt();
				if(numHands < 1){
					System.out.println("The number of hands must be a positive integer above 0.");
				}
			}
		}
		
		//Creates players either way
		for(int players=0; players < numPlayers; players++){
			Player p = new Player();
			playerList.add(p);
		}

		//Creates dealer
		Player dealer = new Player(ISDEALER);
		
		for(int hands=0; hands<numHands; hands++){
			//Create and shuffle deck
			deck.fillBlackjackDeck();
			deck.shuffleDeck();
			
			System.out.println("\n **** HAND#" + (hands+1) + " ****\n" );
			
			dealer.clearHand();
			dealer.fillAndStateHand(deck);
			
			//Let each player hit as much as they want
			for(Player p: playerList){
				boolean playerDone = false;
				p.clearHand();
				p.fillAndStateHand(deck);
				checkIfFirstTwoCardsAces(p);
				while((!playerDone)){
					if(mode == 0){
						playerDone = hitPlayerManual(p);
					}else{
						playerDone = hitPlayerAlgo1(p);
					}
					
					//System.out.println(playerDone + " " + dealerDone); debugging
				}
			}
			
			//Reset the dealer and then let them hit
			boolean dealerDone = false;
			checkIfFirstTwoCardsAces(dealer);
			while(!dealerDone){
				dealerDone = hitDealer(dealer);
			}
			
			//Compare the cards of each player and the dealer to see whether they won
			for(Player p: playerList){
				int comp = compareCards(p, dealer);
				if(comp == 1){
					p.incrementPoints();
					p.incrementTotalHandsPlayed();
				}
				else if(comp == 0){
					p.incrementTotalHandsPlayed();
				}
			}
		}
		//Print out each players total points after all the hands
		double totalPercentage = 0.0;
		double avgPercentWins = 0.0;
		for(Player p:playerList){
			double playerPercentage = ((p.getPoints())/p.getTotalHandsPlayed());
			System.out.println("\n" + p.getName() + " has won " + playerPercentage + "% of their hands. " + ((int)p.getPoints()) + " out of " + ((int)p.getTotalHandsPlayed()) + " hands." );
			totalPercentage += playerPercentage;
		}
		avgPercentWins = totalPercentage/playerList.size();
		System.out.println("\nThe average percentage of wins by algorithm 1 is: " + avgPercentWins + "");
	}	
	
	//************************************************************************************************************************************//
	/*
	 * BASIC METHODS
	 */
	//************************************************************************************************************************************//

	//Returns a boolean that if true means the player is done hitting
	public static boolean hitPlayerManual(Player p){
		if(p.getCurrentScore() >= 21){
			return true;
		}
		System.out.println(p.getName() + " your current score is " + p.getCurrentScore() + " out of the goal of 21, type 1 if you would like to hit and 0 if not: ");
		int hit = reader.nextInt();
		if(hit == 1){
			//If they're going to bust despite having an ace in their hand, change that ace to a 1
			checkIfSwitchAce(p);
			//Then hit them
			//addToHand also states what they drew in console
			p.addToHand(deck.drawTopCard());
			return checkBust(p);
		}
		else{
			//They chose to stop so they are "done"
			return true;
		}
	}
	
	public static boolean hitDealer(Player dealer){
		if(dealer.getCurrentScore() >= 17){
			return true;
		}
		else if(dealer.getCurrentScore() <= 16){
			checkIfSwitchAce(dealer);
			dealer.addToHand(deck.drawTopCard());
		}
		return checkBust(dealer);
	}
	
	public static void checkIfSwitchAce(Player p){
		for(Card c: p.getHand()){
			if(p.getCurrentScore()+deck.peekCard().getValue() > 21){
				//If there is an ace in the hand, set it's value to 1
				if(c.getName().equals("ace")){
					c.setValue(1);
					//Then return so it doesn't set both aces equal to 1
					return;
				}
				//If there is an ace as the next draw and it'll cause a go over, set it equal to 1
				if(deck.peekCard().getName().equals("ace")){
					deck.peekCard().setValue(1);
					return;
				}
			}
		}
	}
	
	//Special case where two aces are drawn right away
	public static void checkIfFirstTwoCardsAces(Player p){
		if(p.getCard(0).getName().equals("ace") && p.getCard(1).getName().equals("ace")){
			p.getCard(1).setValue(1);
		}
	}
	
	
	//Returns a boolean if busted
	public static boolean checkBust(Player p){
		if(p.getCurrentScore()>21){
			System.out.println(p.getName() + " has busted.");
			return true;
		}
		else if(!p.isDealer()){
			System.out.println(p.getName() + " your current score is " + p.getCurrentScore() + " out of the goal of 21.");
			return false;
		}
		return false;
	}
	
	//True signifies beating or tieing atm, will implement point system
	public static int compareCards(Player p, Player dealer){
		if((p.getCurrentScore() > dealer.getCurrentScore() && p.getCurrentScore() <=21) || (p.getCurrentScore() <=21 && dealer.getCurrentScore() >21)){
			//System.out.println("\n" + p + " " + dealer + "\n"); //debug to see values
			//System.out.println("\n Player: " + p.getCurrentScore() + " compared to dealer: " + dealer.getCurrentScore() + "\n");
			System.out.println(p.getName() + " has beaten the dealer, with a score of: " + p.getCurrentScore() + " to the dealer's: " + dealer.getCurrentScore() + ".");
			return 1;
				
		}
		else if((dealer.getCurrentScore() == p.getCurrentScore() && p.getCurrentScore() <=21)){
			System.out.println(p.getName() + " has gotten a push, meaning they tied, with mutual scores of " + p.getCurrentScore() + " and " + dealer.getCurrentScore() + ".");
			return -1;
		}
		//They both bust which means player busted first so dealer wins
		else if((dealer.getCurrentScore() > 21) && (p.getCurrentScore() > 21)){
			//System.out.println("\n" + p + " " + dealer + "\n"); //debug to see values
			//System.out.println("\n Player: " + p.getCurrentScore() + " compared to dealer: " + dealer.getCurrentScore() + "\n");
			System.out.println(p.getName() + " has lost to the dealer, with a score of: " + p.getCurrentScore() + " to the dealer's: " + dealer.getCurrentScore() + ".");
			return 0;
		}
		else{
			//System.out.println("\n" + p + " " + dealer + "\n"); //debug to see values
			//System.out.println("\n Player: " + p.getCurrentScore() + " compared to dealer: " + dealer.getCurrentScore() + "\n");
			System.out.println(p.getName() + " has lost to the dealer, with a score of: " + p.getCurrentScore() + " to the dealer's: " + dealer.getCurrentScore() + ".");
			return 0;
		}
	}	
	
	//************************************************************************************************************************************//
	/*
	 * Automation
	 */
	//************************************************************************************************************************************//
	
	public static boolean hitPlayerAlgo1(Player p){
		if(p.getCurrentScore() >= 17){
			return true;
		}

		else if(p.getCurrentScore() <= 16){
			checkIfSwitchAce(p);
			p.addToHand(deck.drawTopCard());
		}
		return checkBust(p);
	}
	
	public static boolean hitPlayerAlgo2(Player p){
	//	if(p.getCurrentScore())
		return true;
	}

	
}

