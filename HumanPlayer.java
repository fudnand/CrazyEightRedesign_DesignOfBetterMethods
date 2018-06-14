package crazyEightRedesin;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HumanPlayer extends Player{	
	Scanner  keyboard = new Scanner(System.in);
	
	/*This method is for selecting a card from the players hand of cards
	 * Pre-condition: the players hand(pHand) is not null
	 * Post-condition: returns a card from the players hand of cards
	 */
	public Card selectCard(List<Card> pHand) 
	{
		if(pHand.isEmpty()) return null;
		int selection = 0;
		
		System.out.println("Select a card to play");
		selection = keyboard.nextInt();
		
		while(selection<=0 || selection>pHand.size())
		{
			System.out.println("Invalid Card Input. Try a different card: ");
			selection = keyboard.nextInt();
		}
		
		return pHand.get(selection-1);
	}
	/*This method shows the player what available cards they have that can be played
	 * Pre-condition: hand & topCard can't be null
	 * Post-condition: a list of card options that can be played is returned  
	 */
	private List<Card> cardsToPlay(Card topCard, Hand hand)
	{
		if(hand == null || topCard == null) return null;
		
		List<Card> returnList = new ArrayList<Card>();
		for(int i = 0; i< hand.getHand().size(); i++)
		{
			if(topCard.getSuit() == hand.getHand().get(i).getSuit() || topCard.getValue() == hand.getHand().get(i).getValue())
				returnList.add(hand.getHand().get(i));
		}
		
		return returnList;
	}
	/*We added a method call to this method to ensure that it handles the action of playing a card more efficiently
	 * Pre-condition: Top card is not null or hand is not null or the deck is not null
	 * Post-condition: card returned from hand or drawn from deck, matches either the suit of topCard or value of topCard  or 8 
	 */		
	public Card playCard(Card topCard, Deck deck) {
		
		System.out.println("\n\tTop card is " + topCard);
		
		List<Card> playerHand = cardsToPlay(topCard,hand);
		
		System.out.println("\n===================You can play===================\n");
		for(int i = 0; i<playerHand.size();i++)
		{
			System.out.println((i+1)+") "+playerHand.get(i).toString());
		}
		
		return selectCard(playerHand);		
	}
/*	
	public Card playAgain(Card topCard, Hand deck){
		return null;
	}//if deck runs out of card this is used
*/	
}
