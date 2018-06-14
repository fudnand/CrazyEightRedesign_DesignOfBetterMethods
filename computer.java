package crazyEightRedesin;
public class computer extends Player{
	/*We added a method call to this method to ensure that it handles the action of playing a card more efficiently
	 * Pre-condition: Top card is not null or hand is not null or the deck is not null
	 * Post-condition: card returned from hand or drawn from deck, matches either the suit of topCard or value of topCard  or 8 
	 */	
	public Card playCard(Card topCard, Deck deck) {		
		if(topCard ==null || deck == null || hand == null){
			return null;
		}
		Card playThis = findPlayableCard(topCard);
		if (playThis != null){
			return playThis;
		}
		
		boolean played = false;//hasn't played		
		do{ 
			if(deck.cardsLeft() == 0){
				return null;	//if deck is empty return asking for discount pile
			}
			Card j = deck.dealCard();
			if(topCard.getSuit() == j.getSuit()){
				return j;
			}//plays card if suit is the same
			if(topCard.getValue() == j.getValue()|| j.getValue() == 8){
				return j;
			}//plays card if value is the same
			played = false;
			hand.addCard(j);
		}while(!played);
		
		return null;// means deck is empty and they should pick from discount pile
	}
	
	/*This method was added to check for playable cards in a players hand (Functional Cohesion)
	 * Pre-condition- Top card is not null or hand is not null
	 * Post-condition- Returns a card from the hand if it matches the suit or value of top card
	 * or if hand has an 8    
	 */
	public Card findPlayableCard(Card topCard){
		if (topCard == null || hand==null){
			return null;
		}
		for(Card k : hand.getHand()){
			if(topCard.getSuit() == k.getSuit()){
				hand.removeCard(k);
				return k;
			}
			if(topCard.getValue() == k.getValue() || k.getValue() == 8){
				hand.removeCard(k);
				return k;
			}
		}
		return null;
	}	
}
