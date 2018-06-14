package crazyEightRedesin;

import java.util.*;
//for controlling computer gameplay of crazyeights
//we added a couple a couple methods to increase cohesion
public class gameControl implements IGameControl {
	Scanner  keyboard = new Scanner(System.in);	
	IGameView h = new IOHandler();	//can never be null
	Deck deck; 						//at most 52 unique cards and if empty game over 
	ArrayList<computer> players;	//should be more than 0 when game is running 
	Card topCard;					//after game start can't be null. Must be null before game begins. Must be 1 of the unique 52 cards in a deck
	
	
	/*
	 * This method is the class invariant
	 * Precondition: all class variables have been initialized
	 * PostCondition: returns true if valid or false if not
	 */
	public boolean validate(){
		if(h ==null) return false;	
		if(deck == null) return false ; 						
		if(players == null) return false;				
		//if(topCard == null) return false;
		
		return true;
	}
	
	public gameControl(){
		init();
	}

	/*This method is for starting a new game and clearing all previous data 
	 * Precondition: must be first thing to run in a new game
	 * PostCondition: display is set. players, deck and topCard are instantiated. Deck is shuffled
	 */
	@Override
	public void init(){
		String str = "This program lets you play the simple card game,\n" + 
			"crazyEight's.  Crazy Eights uses a standard 52 card pack (no Jokers).  \n"
			+ "The dealer deals (singly) seven cards to each player.  \n"
			+ "The undealt stock is placed face down on the table, \n"
			+ "and the top card of the stock is turned face up and placed \n"
			+ "beside the stock to start this discard pile.\n";
		h.display(str);
		players = new ArrayList<computer>();
		deck = new Deck();   
		deck.shuffle(); 	
		topCard = null;
	}
	/* This method runs the game
	 * Cohesion: 5 deck deals card to players, then deals top card, then plays the game (all in sequence)
	 * PreCondition: All class variables are initialized and not null except topCard (that is null)
	 * PostCondition: Winner is declared, game is run at least once, Game over is console logged.  
	 */
	@Override
	public void runGame() 
	{
		//if(validate() == false) return;
		char playAgain = 'f';       // Record user's response when user is asked whether he wants to play another game.
		int numPlayers = 0;
		
		do{
			System.out.println("How many computer players are going to play?");
			numPlayers = keyboard.nextInt();
			while (numPlayers<2){
				System.out.println("Please players must be more than 1 to play?");
				numPlayers = keyboard.nextInt();
			}

			for(int i=0; i<numPlayers; i++){			
				players.add(new computer());
			}		
			dealCardToPlayers();
			dealTopCard();
			playGame();
			
			h.display("Play again (t/f)" + "?");
			playAgain = h.getInput();
			
			if(playAgain == 't'){
				players.clear();
			
				init();
				
			}
		} while (playAgain == 't');
		 h.display("\n **** Game Over***");
	}// runs the game for computer 	
	
	/* This method plays the game until game over
	 * PreCondition: All class variables have been initialized
	 * PostCondition: Game has ended 
	 * Cohesion: 7
	 */	
	public void playGame() {	
		while(playRound() != false){//plays and checks for a winner		
			System.out.println("...................NEXT PLAY RND....................");
		}
	}
	
	/* This method deals card to the Players
	 * PreCondition: Deck is not Empty, players has been initialized
	 * PostCondition: Cards have been dealt to players
	 * Cohesion: 7
	 */	
	private void dealCardToPlayers(){
		if(validate() == false) return;
			for(int i=0; i<players.size(); i++){
			for (int j=0; j<7; j++){	//shares cards between players
				players.get(i).addCard(deck.dealCard());
			}
		}//deals 7 cards to all players 
	}
	
	
	/*This method deals the topCard for the game
	 * Precondition: deck is not empty
	 * PostCondition: topCard is dealt and  has topCard
	 * Cohesion: 7
	 */
	private void dealTopCard(){	
		if(validate() == false) return;
		topCard = deck.dealCard();//top card to be played on			
	}
	
	/* Cohesion: 6
	 * Plays a Round of the Game 
	 * PreCondition: all class variables have been instantiated (including topCard), players have been dealt cards. 
	 * PostCondition: if deck runs out of card returns false, if someone wins return false, or else return true  
	 */
	private boolean playRound() {	
		if(validate() == false) return false;
		Card played;
		for(int i =0; i<players.size(); i++){
			int temp = (i+1); //prevent computer 0 output	
			
			played = players.get(i).playCard(topCard, deck);
						
			if(played == null){
					h.display("No winner");
					return false;
				}
			
			else{
				topCard = played;
				h.display("Player-"+ temp + " played: "+ played.toString());
				played = null;
				
				if(players.get(i).hand.getCardCount()-1 == 0){ //checks for winner of game
					h.display("Hoorayy!! Computer-" + temp + " has won.");
					topCard = null;
					return false;
				}
			}
		}		
		return true;	
	}
}

//this class is for changing game views and displaying game data
class IOHandler implements IGameView{
	Scanner  sc = new Scanner(System.in);
	
	/*
	 * To console log messages to screen
	 * Precondition: message can't be an empty string 
	 * PostCondition: Message is printed to screen
	 * Cohesion: 7
	 */
	public void display(String message) {
		if(message.length() == 0) return;
		System.out.println(message);	
	}
	
	/*
	 * This method is for getting user input 
	 * PreCondition: game must have run at least 1 time when called 
	 * PostCondition: Must return a Char between (t,T,f,F)\
	 * Cohesion: 7
	 */	
	public char getInput() {
		boolean isCorrectInput = true;
		char use = 0;
		use = sc.next().charAt(0);
		if(use!='t' || use !='f' || use !='F' ||use !='T'){
			while(!isCorrectInput){
				System.out.print("Please respond with an expected character:  "); 
				use = sc.next().charAt(0);
			}
		}      
		return use;
	}
	
	
	
	
}
