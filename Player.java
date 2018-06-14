package crazyEightRedesin;

abstract class Player 
{
	protected Hand hand;//hand can't be null but it can be empty
	
	public Player()
	{
		hand = new Hand();
	}
	
	/*This method adds a card to the players hand either computer or player
	 * Pre-Condition: card is not null
	 * Post-Condition: card was added to the hand 
	 * Cohesion: 7
	 */
	public void addCard(Card card)
	{
		if (card != null){
			hand.addCard(card);
		}		
	}
	
	abstract public Card playCard(Card topCard, Deck deck);
}
