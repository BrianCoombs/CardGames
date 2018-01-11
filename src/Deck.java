import java.util.ArrayList;

public class Deck {
	protected ArrayList<Card> deck;
	
	public Deck(){
		deck = new ArrayList<Card>();
	}
	
	//Fills another deck with same cards then randomly pulls from it to refill the original deck
	public void shuffleDeck(){
		ArrayList<Card> deckrandom = new ArrayList<Card>();
		deckrandom.addAll(deck);
		int deckSize = deck.size();
		deck.clear();
		for(int i=0; i<deckSize-1; i++){
			double rand = Math.random();
			int randIndex = (int) (rand*(deckrandom.size()));
			deck.add(deckrandom.get(randIndex));
			deckrandom.remove(randIndex);
		}
	}
	
	//Returns the top card of the deck and puts it at the bottom of the deck
	public Card drawTopCard(){
		Card drawnCard = deck.get(0);
		deck.add(deck.get(0));
		deck.remove(0);
		return drawnCard;
	}
	//Peeks the top card of the deck
	public Card peekCard(){
		return deck.get(0);
	}
}
