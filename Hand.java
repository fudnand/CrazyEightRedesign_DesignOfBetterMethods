package crazyEightRedesin;
import java.util.ArrayList;
import java.util.Collections;
public class Hand {
	private ArrayList<Card> hand;   // The cards in the hand of a player
	
    public Hand() {
        hand = new ArrayList<Card>();
    }//creates new empty hand
    
    public void clear() {
        hand.clear();
    }//clears hand 
    
    public void addCard(Card c) {
        if (c == null)
            throw new NullPointerException("Can't add a null card to a hand.");
        hand.add(c);
    }//adds card if not null
    
    public void removeCard(Card c) {
        hand.remove(c);
    }//removes card c if in the hand
    
    public Card removeCard(int position) {
        if (position < 0 || position >= hand.size())
            throw new IllegalArgumentException("Position does not exist in hand: "
                    + position);
        return hand.remove(position);
    }//plays/removes card at a particular position
    
    public int getCardCount() {
        return hand.size();
    }//returns number of cards left to be played
    
    public ArrayList<Card> getHand(){
    	return hand;
    }
    
    public void shuffle(){
    	Collections.shuffle(hand);
    }
    
    public void useDiscardPile(Card topCard){
    	hand.remove(topCard);
    	shuffle();
    }
    
    public void sortBySuit() {
        ArrayList<Card> newHand = new ArrayList<Card>();
        while (hand.size() > 0) {
            int pos = 0;  // Position of minimal card.
            Card c = hand.get(0);  // Minimal card.
            for (int i = 1; i < hand.size(); i++) {
                Card c1 = hand.get(i);
                if ( c1.getSuit() < c.getSuit() ||
                        (c1.getSuit() == c.getSuit() && c1.getValue() < c.getValue()) ) {
                    pos = i;
                    c = c1;
                }
            }
            hand.remove(pos);
            newHand.add(c);
        }
       // Collections.sort(list, c);
        hand = newHand;
        //Collections.sort(list, c);
    }//sorts cards in suits by hand 

    public void sortByValue() {
        ArrayList<Card> newHand = new ArrayList<Card>();
        while (hand.size() > 0) {
            int pos = 0;  // Position of minimal card.
            Card c = hand.get(0);  // Minimal card.
            for (int i = 1; i < hand.size(); i++) {
                Card c1 = hand.get(i);
                if ( c1.getValue() < c.getValue() ||
                        (c1.getValue() == c.getValue() && c1.getSuit() < c.getSuit()) ) {
                    pos = i;
                    c = c1;
                }
            }
            hand.remove(pos);
            newHand.add(c);
        }
        hand = newHand;
    }//sorts cards in hand by value

    
}
