import java.util.ArrayList;
import java.util.Arrays;

//To-do: Implement extends of general deck object that allows shuffles and peeks
public class BlackjackDeck extends Deck{
	
	public BlackjackDeck(){
		super();
	}
	
	//Doesn't need constructor bc uses deck constructor

	public void fillBlackjackDeck(){
		ArrayList<String> suits = new ArrayList<String>(Arrays.asList("Hearts", "Diamonds", "Clubs", "Spades"));
		for(String suit: suits){
			for(int i=2; i<=14; i++){
				if(i<=10){
					deck.add(new Card(suit, i, Integer.toString(i)));
				}
				else if(i==11){
					deck.add(new Card(suit, 10, "jack"));
				}
				else if(i==12){
					deck.add(new Card(suit, 10, "queen"));
				}
				else if(i==13){
					deck.add(new Card(suit, 10, "king"));
				}
				else if(i==14){
					deck.add(new Card(suit, 11, "ace"));
				}
			}
		}
	}

}
