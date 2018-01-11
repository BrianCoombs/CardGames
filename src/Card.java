
public class Card {
	private String suit;
	private int value;
	private String name;
	
	public Card(){
		suit = "";
		value = 0;
		name = "";
	}
	
	public Card(String s, int n, String Name){
		this.suit = s;
		this.value = n;
		this.name = Name;
		
	}
	
	public String getSuit(){
		return suit;
	}
	public void setSuit(String suit){
		this.suit = suit;
	}
	
	public int getValue(){
		return value;
	}
	public void setValue(int num){
		this.value = num;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	
	
}
