import java.util.ArrayList;

public class Player {
	private ArrayList<Card> hand;
	private int handStartSize;
	private int currentScore;
	private String name;
	private static int playerCount;
	private boolean isDealer;
	private double points;
	private double totalHandsPlayed;
	//Betting vars-->
	//private int money;
	//private int winnings;
	
	
	//Blackjack player makes handStartSize 2, could be changed in future to be variable if playing gofish etc.
	public Player(){
		this.hand = new ArrayList<Card>();
		this.handStartSize = 2;
		this.currentScore = 0;
		this.name = ("Player" + (playerCount+1));
		this.isDealer = false;
		playerCount++;
	}
	
	public Player(boolean isDealer){
		//Use generic constructor
		//Player();
		//this.isDealer = isDealer;
		this.hand = new ArrayList<Card>();
		this.handStartSize = 2;
		this.currentScore = 0;
		this.name = ("The Dealer");
		this.isDealer = isDealer;
		playerCount++;
	}
	
	public Player(String playerName){
		this.hand = new ArrayList<Card>();
		this.handStartSize = 2;
		this.currentScore = 0;
		this.name = playerName;
		playerCount++;
	}
	
	//Updates the score and then retuns it
	public int getCurrentScore() {
		updateScore();
		return currentScore;
	}
	//Sets the score, shouldn't be needed unless in debugging
	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}
	//Returns the entire hand arraylist for iteration
	public ArrayList<Card> getHand() {
		return hand;
	}
	//Returns the card at index i of the hand
	public Card getCard(int i){
		return hand.get(i);
	}
	//For resetting an entire playerhand
	public void setHand(ArrayList<Card> playerHand) {
		this.hand = playerHand;
	}
	
	//Adds a card to the hand and states what that card is
	public void addToHand(Card c){
		hand.add(c);
		if(!isDealer){
			System.out.println(name + " drew a(n) " + c.getName() + " of " + c.getSuit());
		}
		
	}
	
	public boolean isDealer(){
		return isDealer;
	}
	
	//Returns the players name
	public String getName() {
		return name;
	}
	public void setName(String playerName) {
		this.name = playerName;
	}
	
	//Updates score
	public void updateScore(){
		currentScore = 0;
		for(int i=0; i< hand.size(); i++){
			currentScore+= hand.get(i).getValue();
		}
	}
	
	public void clearHand(){
		hand.clear();
	}
	
	
	//Fills hand with cards up to max start size
	public void fillAndStateHand(BlackjackDeck deck){
		if(isDealer){
			for(int i=0; i<handStartSize; i++){
				addToHand(deck.drawTopCard());
			}
			//Only shows top card to player
			System.out.println("The dealer's face-up card is a(n) " + getCard(0).getName() + " of " + getCard(0).getSuit());
		}
		else{
			for(int i=0; i<handStartSize; i++){
				addToHand(deck.drawTopCard());
			}
		}
	}
	public double getPoints() {
		return points;
	}
	public void incrementPoints() {
		this.points = (this.points+1);
	}
	public double getTotalHandsPlayed() {
		return totalHandsPlayed;
	}
	public void incrementTotalHandsPlayed() {
		this.totalHandsPlayed = (this.totalHandsPlayed+1);
	}
}
