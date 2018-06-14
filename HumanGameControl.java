package crazyEightRedesin;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HumanGameControl implements IGameControl
{
	Scanner  keyboard = new Scanner(System.in);
	IGameView h = new IOHandler();
	Deck deck;
	boolean gamEnd; 
	List<Player> players;	
	Hand discardPile;
	
	public HumanGameControl()
	{
		init();
	}
	
	public void init(){
		
		String str = "This program lets you play the simple card game,\n" + 
			"crazyEight's.  Crazy Eights uses a standard 52 card pack (no Jokers).  \n"
			+ "The dealer deals (singly) seven cards to each player.  \n"
			+ "The undealt stock is placed face down on the table, \n"
			+ "and the top card of the stock is turned face up and placed \n"
			+ "beside the stock to start this discard pile.\n";
		
		h.display(str);
		deck = new Deck();   
		deck.shuffle(); 
		gamEnd = false;		
	}

	public void runGame() 
	{
		char playAgain = 'f';       // Record user's response when user is asked whether he wants to play another game.
		
		
		int numPlayers = 0, numComp = 0;
		
		do{
			players = new ArrayList<Player>();
			
			System.out.println("How many Human players are going to play?");
			
			numPlayers = keyboard.nextInt();
			
			while (numPlayers <1)
			{
				System.out.println("There must be at least 1 human player");
				numPlayers = keyboard.nextInt();
			}

			for(int i=0; i <= numPlayers; i++)
			{
				players.add(new HumanPlayer());
			}	
			
			playGame();
			
			h.display("Play again (t/f)" + "?");
			
			playAgain = h.getInput();
			
			if(playAgain == 't'){
				init();
			}
		} while (playAgain == 't');
		
		 h.display("\n **** Game Over***");
		 
	}// runs the game for computer 	
	
	public void playGame() 
	{
		deck = new Deck();   
		deck.shuffle(); 
		gamEnd = false;	
		
		for(int i=0; i<players.size(); i++)
		{
			for (int j = 0; j < 7; j++)		//shares cards between players
			{	
				players.get(i).addCard(deck.dealCard());
			}
		}	//deals 7 cards to all players
		
		Card topCard = deck.dealCard();		//top card to be played on
		
		int numRound = 1; 	
		
		discardPile = new Hand();
		discardPile.addCard(topCard);
		gamEnd = true;
		
		while(gamEnd)
		{					//plays and checks for a winner
			if (deck.cardsLeft() ==0)
			{
				gamEnd = false;
				
			}
			playRound();
		}
	}
	
	private boolean playRound() 
	{	
		for(int i = 0; i < players.size()-1; i++){
			System.out.println("\nPlayer-"+ (i+1)+" has "+players.get(i).hand.getHand().toString());
			Card topCard = discardPile.getHand().get(discardPile.getCardCount()-1);
			
			if(deck.cardsLeft() == 0)
			{
				discardPile.removeCard(topCard);
				discardPile.shuffle();
			}
			
			Card played = players.get(i).playCard(topCard, deck);
			players.get(i).hand.removeCard(played);
			
			if(played == null)
			{
				inconclusive(i);
				players.get(i).hand.addCard(deck.dealCard());
			}
			else
			{
				int temp = (i+1); 		//prevent computer 0 output	
				
				playedCard(temp, played);
				
				if(players.get(i).hand.getCardCount() == 0)
				{ 						//checks for winner of game
					winner(temp);
					gamEnd = false;
					return gamEnd;
				}
				
				discardPile.addCard(played);
			}
			
			
		}		
		return gamEnd;	
	}
	
	public void playedCard(int player, Card played)
	{
		System.out.print("Player-" + player+" played-");
		h.display(played.toString());
	}
	
	private void winner(int playerNumb)
	{
		String msg = "Hoorayy!! Computer-" + playerNumb + " has won.";
		h.display(msg);
	}	
	
	private void inconclusive(int playerNumb)
	{
		h.display("------------Player-"+(playerNumb+1) + " Ran out of "
				+ "playable cards, match over!---------------\n");
	}

}
