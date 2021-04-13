package wumpus;

import java.util.Random;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.List;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;


import wumpus.Room;



import javax.swing.JTextPane;
import javax.swing.JProgressBar;


public class game extends JFrame {

	public static int ROWS = 0;  // 
	public static int COLS = 0;

	//
	public static final int CELL_SIZE = 100; // cell width and height (square)
	public   int CANVAS_WIDTH =0;  // 
	public  int CANVAS_HEIGHT = 0;
	public static final int GRID_WIDTH = 2;                   // 
	public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2; // 
	public int score = 0;
	int fleche =0;

	public enum GameState {
		PLAYING, DRAW, H1_WON, H2_WON,H1_QUIT,H2_QUIT,H1_GRIMP
	}
	private GameState currentState;  // 

	private Player currentPlayer;  // 
	protected Player h1;    // 
	protected AI h2;    // 

	private Board board   ; // 
	private DrawCanvas canvas; // Drawing canvas (JPanel) for the game board
	private JLabel statusBar;  // 
	
	
	

	private DrawRoom[][] squares = new DrawRoom[ROWS][COLS]; //
	private ImageIcon imageH1[] = new ImageIcon[5];//
	private ImageIcon imageH2[] = new ImageIcon[4];//
	private ImageIcon imageBump[] = new ImageIcon[1];//
	private int currentImageH1; //
	private int currentImageH2; //
	
	public game(int ordre) {
		ROWS = ordre;
		COLS = ordre;
		CANVAS_WIDTH = CELL_SIZE * COLS; 
		CANVAS_HEIGHT = CELL_SIZE * ROWS;
		squares =new DrawRoom[ROWS][COLS];
		board = new Board();
		Room startRoom = board.getRoom(0,0);
		startRoom.setHints();
		h1 = new Player(startRoom);
		h2 = new AI(startRoom);
		
		canvas = new DrawCanvas();  
		canvas.setFocusable(true);
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		canvas.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));

		
		canvas.setLayout(new GridLayout(ROWS,COLS));
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLS; j++){
				squares[i][j] = new DrawRoom();
				squares[i][j].setBackground(new Color(255, 193, 84));
				squares[i][j].setBorder(BorderFactory.createLineBorder(new Color(255, 226, 46)));
				canvas.add(squares[i][j]);
			}

		   
		
			
		
		
		
		
	
	
		
	
		
	
		
		statusBar = new JLabel(" ");
		statusBar.setBackground(new Color(255, 175, 175));
		statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 20));
		statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));


		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.setBackground(Color.WHITE);
		 
	
		cp.add(canvas, BorderLayout.CENTER); 
		cp.add(statusBar, BorderLayout.PAGE_END);
		
		


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();  
		setTitle("Wumpus World");
		setVisible(true);  // 

		currentState = GameState.PLAYING; 
		currentPlayer = h1;       
		currentImageH1 = 0;
		currentImageH2 = 0;
	}

	
	public void initGame() {
		board = new Board();
		Room startRoom = board.getRoom(0,0);
		startRoom.setHints();
		h1 = new Player(startRoom);
		h2 = new AI(startRoom);
		score = 0;
		fleche = 0;
		currentState = GameState.PLAYING; 
		currentPlayer = h1;       
		currentImageH1 = 0;
		currentImageH2 = 0;

		for (int i = 0; i < ROWS; i++)  
			for (int j = 0; j < COLS; j++){
				squares[i][j].hidePics();
			}

		repaint();
	}




	
	public void updateGame(Player currentPlayer, char command) {
		int currentPlayerLocation = currentPlayer.getCurrentRoom().getLocation(); //get current player location

		switch(Character.toUpperCase(command)){
		case 'F': 
			if(currentPlayer == h1) {
				squares[currentPlayerLocation/ROWS][currentPlayerLocation%COLS].pics[2][0].setVisible(false);
				score --;
			}else {
				squares[currentPlayerLocation/ROWS][currentPlayerLocation%COLS].pics[2][2].setVisible(false);
			}currentPlayer.avancer(); 

			break;
		case 'L': 
			currentPlayer.tournerGauche(); 
			if(currentPlayer == h1){ 
				score --;
				if (currentImageH1 == 0) 
					currentImageH1 = 3;
				else
					currentImageH1 --;
			}
			else{
				if (currentImageH2 == 0) 
					currentImageH2 = 3;
				else
					currentImageH2 --;
			}
			break; 
		case 'R': 
			currentPlayer.tournerDroit(); 
			if(currentPlayer == h1){ 
				score --;
				if (currentImageH1 == 3) 
					currentImageH1 = 0;
				else
					currentImageH1 ++;
			}
			else{
				if (currentImageH2 == 3) 
					currentImageH2 = 0;
				else
					currentImageH2 ++;
			}
			break;
		case 'G': 

			if(currentPlayer == h1){
				if(currentPlayer.grabGold()){
				squares[currentPlayerLocation/ROWS][currentPlayerLocation%COLS].pics[1][2].setVisible(true); //show gold picture
				score = score+1000;
				}

			}else{

				if(currentPlayer.grabGold()){
					squares[currentPlayerLocation/ROWS][currentPlayerLocation%COLS].pics[1][2].setVisible(true); //show gold picture
				}
			}
			break;
		case 'S': 
			
			currentPlayer.shoot();
			if((currentPlayer == h1) && (fleche < 2) )
			{	score = score-10;
				fleche ++;
				if(currentPlayer.isDragonKiller()){
					squares[currentPlayerLocation/ROWS][currentPlayerLocation%COLS].pics[1][1].setVisible(true); //show roar picture
					currentPlayer.resetDragonKiller();
					
				}
			}else
			{
				if(currentPlayer.isDragonKiller()){
				squares[currentPlayerLocation/ROWS][currentPlayerLocation%COLS].pics[1][1].setVisible(true); //show roar picture
				currentPlayer.resetDragonKiller();
				}
			}
			break;
		case 'Q': 
			 
			currentPlayer.quit();
			break;
		

		case 'T':
				if(squares[currentPlayerLocation/ROWS][currentPlayerLocation%COLS]==squares[0][0]) //show roar picture
				{
						if(currentPlayer == h1){ 
						
							currentImageH1 = 4;
						
						
						currentPlayer.grimper();
						
					}
				}

		break;
	


		}


		if (hasWon(currentPlayer)) {  
			currentState = (currentPlayer == h1) ? GameState.H1_WON : GameState.H2_WON;
		} else if (isDraw()) { 
			currentState = GameState.DRAW;
			
			score = score-1000;
		}else if (quit()) {  
			currentState = GameState.H1_QUIT;
			
			
		}
		else if (grimpe()) {  
			currentState = GameState.H1_GRIMP;
			
			
		}
		
		

		
	}

	
	public boolean isDraw() {
		if (!h1.isAlive() )
			return true;
		return false;
	}

	public boolean quit() {
		if (!h1.isquit() )
			return true;
		return false;
	}

public boolean grimpe() {
		if (h1.isgrimpant() )
			return true;
		return false;
	}

public boolean back() {
		if (!h1.isgrimpant() )
			return true;
		return false;
	}

			



	
	public boolean hasWon(Player currentPlayer) {
		return currentPlayer.hasGold();
	}

	class DrawRoom extends JPanel { 
		public JLabel[][] pics = new JLabel[3][4]; 

		public DrawRoom() {

			imageH1[0] = changeImageSize("src\\wampus\\hunter10.png");
			imageH1[1] = changeImageSize("src\\wampus\\hunter11.png");
			imageH1[2] = changeImageSize("src\\wampus\\hunter12.png");
			imageH1[3] = changeImageSize("src\\wampus\\hunter13.png");
			imageH2[0] = changeImageSize("src\\wampus\\hunter20.png");
			imageH2[1] = changeImageSize("src\\wampus\\hunter21.png");
			imageH2[2] = changeImageSize("src\\wampus\\hunter22.png");
			imageH2[3] = changeImageSize("src\\wampus\\hunter23.png");
			imageH1[4] = changeImageSize("src\\wampus\\quitte.jpg");
			
			setLayout(new GridLayout(3,4));

			pics[0][0] = new JLabel(changeImageSize("src\\wampus\\breeze.png"));
			pics[0][1] = new JLabel(changeImageSize("src\\wampus\\stench.png"));
			pics[0][2] = new JLabel(changeImageSize("src\\wampus\\glitter.jpg"));
			pics[0][3] = new JLabel(changeImageSize("src\\wampus\\wall.png"));
			
			pics[1][0] = new JLabel(changeImageSize("src\\wampus\\pitc.png"));
			pics[1][1] = new JLabel(changeImageSize("src\\wampus\\roar.png"));
			pics[1][2] = new JLabel(changeImageSize("src\\wampus\\gold.png"));
			pics[1][3] = new JLabel(changeImageSize("src\\wampus\\ss.jpg"));
			
			pics[2][0] = new JLabel(imageH1[currentImageH1]);
			pics[2][1] = new JLabel(changeImageSize("src\\wampus\\wumpusc.gif"));
			pics[2][2] = new JLabel(imageH2[currentImageH2]);
			pics[2][3] = new JLabel(changeImageSize("src\\wampus\\rr.jpg"));
			

			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 4; j++){
					pics[i][j].setVisible(false);
					add(pics[i][j]);

				}
		}

		public void showPics(String s){ 
			String[] symbols = s.split("[ ]");
			for (int i = 0; i < symbols.length; i++){
				
				switch (symbols[i]){
				case "B": pics[0][0].setVisible(true); break;
				case "S": pics[0][1].setVisible(true); break;
				case "G": pics[0][2].setVisible(true); break;
				case "P": pics[1][0].setVisible(true); break;
				case "R": pics[1][1].setVisible(true); break;
				case "D": pics[1][2].setVisible(true); break;
				case "C": pics[0][3].setVisible(true); break;
				case "H1": pics[2][0].setIcon(imageH1[currentImageH1]);
				pics[2][0].setVisible(true); break;
				case "W": pics[2][1].setVisible(true); break;
				case "H2": pics[2][2].setIcon(imageH2[currentImageH2]);
				pics[2][2].setVisible(true); break;
				}
			}
		}

		public void hidePics(){ 
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 4; j++)
					pics[i][j].setVisible(false);
		}
	}


	public ImageIcon changeImageSize(String fileName){ 
		ImageIcon myIcon = new ImageIcon(fileName);
		Image img = myIcon.getImage();
		Image newImg = img.getScaledInstance(CELL_SIZE/3, CELL_SIZE/3, java.awt.Image.SCALE_SMOOTH);
		ImageIcon newIcon = new ImageIcon(newImg);
		return newIcon;
	}

	
	class DrawCanvas extends JPanel {
		public DrawCanvas(){
			
			addKeyListener(new KeyAdapter(){
				@Override 
				public void keyTyped(KeyEvent e){
					char command = e.getKeyChar();
					if (currentState == GameState.PLAYING) {
						updateGame(currentPlayer, command); 
						
						Player nextPlayer = (currentPlayer == h1 ? h2 : h1);
						if (nextPlayer.isAlive()) {
							currentPlayer = nextPlayer;
						}
					} else if (Character.toUpperCase(command) == 'A') {     
						initGame();
					}
					



					
					repaint();  
				}
			});
		}

		@Override
		public void paintComponent(Graphics g) {  
			super.paintComponent(g);    
			setBackground(Color.WHITE); 

			int pl1 = h1.getCurrentRoom().getLocation(); 
			int pl2 = h2.getCurrentRoom().getLocation(); 

			for(int i = 0; i < ROWS; i++) // 
				for (int j = 0; j < COLS; j++){ // 
					if (board.rooms[i][j].isShown()){
						ArrayList<Character> perceptions = board.rooms[i][j].perceptions();
						String status = new String();
						for (Character ch: perceptions) {
							status += ch + " ";
						}
						if ((i == (pl1/ROWS)) && (j == (pl1%COLS)))
							status += "H1 ";
						if ((i == (pl2/ROWS)) && (j == (pl2%COLS)))
							status += "H2 ";
						squares[i][j].showPics(status);
					}
				}

			
			if (currentState == GameState.PLAYING) {
				statusBar.setForeground(Color.BLACK);
				if (currentPlayer == h1) {
					statusBar.setText("Votre Tour" + " : Votre score est" +String.valueOf(score));
				} else {
					statusBar.setText("Le tour de l'ennemi");
					h2.analyzeRoom();
					updateGame(h2, h2.decideMove());
					
					Player nextPlayer = (currentPlayer == h1 ? h2 : h1);
					if (nextPlayer.isAlive()) {
						currentPlayer = nextPlayer;
					}
					repaint();
				}
			} else if (currentState == GameState.DRAW) {
				statusBar.setForeground(Color.BLACK);
				statusBar.setText("ECHEC Vous êtes mort"+ " : Votre score est :" +String.valueOf(score)+ " Cliquer sur A pour recomencer");
			} else if (currentState == GameState.H1_WON) {
				statusBar.setForeground(Color.GREEN);
				statusBar.setText("SUCCES Félicitations!" + " : Votre score est :" +String.valueOf(score)+ " Cliquer sur A pour recomencer");
			} else if (currentState == GameState.H2_WON) {
				statusBar.setForeground(Color.RED);
				statusBar.setText(" ECHEC Dommage ,Chasseur noir a gagné!" + " : Votre score est :" +String.valueOf(score)+ " Cliquer sur A pour recomencer");
			} else if (currentState == GameState.H1_QUIT) {
				statusBar.setForeground(Color.BLACK);
				statusBar.setText("Vous êtes  SORTIS" + " : Votre score est :" +String.valueOf(score)+ " Cliquer sur A pour recomencer");
			}else if (currentState == GameState.H1_GRIMP) {
				statusBar.setForeground(Color.BLACK);
				statusBar.setText("Vous avez grimpé" + " : Votre score est :" +String.valueOf(score)+ " Cliquer sur A pour recomencer");
			}

		}
	}

	
	
	
	
	
	
	




	




	

 class Player
{
 private Room currentRoom;
 private int dir;
 private boolean alive;
 private int fleche;
 private boolean gold;
 private boolean jouant;

 private boolean grimpant;
 private boolean dragonKiller;
 
 public Player (Room start)
 {
  currentRoom = start;
  dir = Board.EAST;
  alive = true;
  fleche = 2;
  gold = false;
  grimpant = false;
  jouant = true;
 
 dragonKiller = false;
 }
 
 public Player () {
	  
 }
 
 // si la cellule suivante (en face du joueur )est null
 // le joueur ne peut pas avancer , il recoit une perception Bump ,(Mur)
 public boolean avancer(){
  
  Room next = currentRoom.getNeighbors()[dir]; 
  if(next==null){
    
    currentRoom.setBump(); 
   return false;

  }
  else{
   currentRoom = next;
   currentRoom.setHints();
   if (currentRoom.hasDragon() || currentRoom.isPit())
    alive = false;
  }
  return true;
 }
 
 public void tournerGauche(){
  dir = dir==0?3:dir-1;
 }
 
 public void tournerDroit(){
  dir = dir==3?0:dir+1;
 }
 
 public Room getCurrentRoom(){
  return currentRoom;
 }
 
 public boolean hasGold(){
  return gold;
 }
 //ramasser l'or
 public boolean grabGold(){
  gold = currentRoom.grabGold();
  return gold;
 }
 //s'il a tué le wumpus on renvoit true, false sinon
 public boolean isDragonKiller(){
  return dragonKiller;
 }
 
 public void resetDragonKiller(){
  dragonKiller = false;
 }
 // S4IL EST VIVANT => true , false sinon
 public boolean isAlive(){
  return alive;
 }


 

 
 public boolean hasArrow(){
  if (fleche > 0)
    return true;
  return false;
 }
 


// tuer le wumpus s'il a une fleche ou deux , 
 //avancer 

 public void shoot(){
  if(fleche==0)
   return;
  fleche --;
  Room r = currentRoom;
  while(r!=null){
   if(r.hasDragon()){
    r.killDragon();
    dragonKiller = true;
    return;
   }
   r = r.getNeighbors()[dir];
  }
 }
 
 public void quit(){
   jouant = false;
 }
// sortir de la case 1X1
public void grimper(){
   grimpant = true;
 }


public boolean isgrimpant(){
     return grimpant;
   }
   

public void isback(){
     grimpant = false;
   }
   
 
 public boolean isquit(){
	   return jouant;
	 }
	 
 
 
 public String getDirectionString(){
  return Board.DIRS[dir];
 }
 
}
	
	
	
	
	
	
	
	public static class Board
{
	public  static int NORTH = 0;
	public static  int EAST = 1;
	public static int SOUTH = 2;
	public static int WEST = 3;
	public static String[] DIRS = {"NORTH", "EAST", "SOUTH", "WEST"};
	public static int ROWS = 0;  // 
	public static  int COLS = 0;
	public static final int PITP = 20; //20% 
	public static final int WUMPUSES = 1; //  
	public Room[][] rooms;
	private int[] randomOrdering;
	
	public Board ()
	{	

		ROWS = game.ROWS;
		COLS = game.COLS;
		
		rooms = new Room[ROWS][COLS];

		for(int i = 0; i<ROWS; i++)
			for(int j = 0; j<COLS; j++)
				rooms[i][j] = new Room(i,j);
		for(int i = 0; i<ROWS; i++)
			for(int j = 0; j<COLS; j++)
				rooms[i][j].initNeighbors(rooms);

		
		Random randomGenerator = new Random();
		randomOrdering = new int[ROWS*COLS - 1];
		for(int i = 0; i< randomOrdering.length; i++)
			randomOrdering[i] = i + 1; //
		for(int i = 0; i<randomOrdering.length; i++){
			int t = randomOrdering[i]; 
			int n = randomGenerator.nextInt(randomOrdering.length);
			randomOrdering[i] = randomOrdering[n];
			randomOrdering[n] = t;
		}
		// mettre les puits , le wampus et l'or
// PITP = 20 , donc vingt pourcents des cases contiendront un puit 
// une seule case contiendra le wumpus et une autre 
// contiendra l'or	
		
		int numPits = ROWS*COLS*PITP/100;
		for(int i = 0; i < numPits; i++){
			randomRoom(i).makePit();
		}
		randomRoom(numPits).spawnDragon();
		
		randomRoom(numPits + 2).depositGold();

	}


// choisir une cellule au hasard
	private Room randomRoom(int i){
		int rnumber = randomOrdering[i];
		int x = rnumber/ROWS;
		int y = rnumber%ROWS;
		return rooms[x][y];

	}

	public Room getRoom(int x, int y){
		return rooms[x][y];
	}

}


	
	
	
	
	

 class AI extends Player
{
	private Room currentRoom;
	private int dir;
	private boolean alive;
	private int fleche;
	private boolean gold;
	private boolean dragonKiller;
	private Room previousRoom;

	public AI (Room start) {
		currentRoom = start;
		dir = Board.EAST;
		alive = true;
		fleche = 2;
		gold = false;
		dragonKiller = false;
	}



// on marque la cellule comme visited et sur
	//si la liste des perceptions est vide , on marque les cellules
	//adjacentes comme safe
	// si la liste des perceptions contient ,S(stench) ou B (breeze)

	//on marque les cellules comme unsafe
	// si la liste contient un G , on met à jour le champ gold à true,  




	public void analyzeRoom() {
		currentRoom.setVisited();
		currentRoom.setSafe();
		ArrayList<Character> perceptions = currentRoom.perceptions();

		if (perceptions.isEmpty()) {
			markNeighorborsSafe();
		} else {
			for (char ch: perceptions) {
				switch (ch) {
				case 'S':
					markUnSafe(ch);
					break;
				case 'B':
					markUnSafe(ch);
					break;
				case 'G':
					this.gold = true;
					break;
				}
			}
		}
	}
	
	//si la liste des perceptions est vide  , on enleve les dangers wumpus et puit
	
	private void checkNeighorbors () {
		if (currentRoom.perceptions().isEmpty()) {

			for (Room rm: currentRoom.getNeighbors()) {
				rm.removeDanger('W');
				rm.removeDanger('P');
				rm.setPitRisk(0.0);
				rm.setWumpusRisk(0.0);
			}
		}
	}


//s'il existe des cellules adjacentes , et qu'elles ne sont pas safe , 
	// on leur met le danger passé en paramètre , qui n'est que la perception 
	//envoyée par la méthode analyseRoom
	//on leur affecte un risque 





	private void markUnSafe(char danger) {
		switch (danger) {
		case 'S':
			for (Room rm: currentRoom.getNeighbors()) {
				if (rm != null && !rm.getSafe()) {
					rm.setDangers('W');
					rm.setWumpusRisk(calculateRiskValue(rm));
				}
			}
			break;
		case 'B':
			for (Room rm: currentRoom.getNeighbors()) {
				if (rm != null && !rm.getSafe()) {
					rm.setDangers('S');
					rm.setPitRisk(calculateRiskValue(rm));
				}
			}
		}
	}


//le risque est 0 pour une cellule safe, 
	//si une cellule est visitée , elle a une liste de perception soit vide soit non vide
	//si la liste des perceptions n'est pas vide, il y aura peut être un danger(sauf si la perception est G pour gold)
// on augmente alors le jeu de 1 , pour chaque perception





	private int calculateRiskValue (Room room) {
		int riskReturn = 0;
	for (Room rm: room.getNeighbors()) {
			if (rm != null && rm.getVisited()) {
				for (char danger: rm.perceptions()) {
					if (danger != ' ') {
						riskReturn ++ ;
					}
				}
			}
		}
		return riskReturn;
	}


	//marquer les cellules adjacentes , comme unvisited
	private void markNeighorborsUnVisited(Room rm) {
		for (Room room: rm.getNeighbors()) {
			if (room != null) {
				room.unSetVisited();
			}
		}
	}
//marquer les cellules adjacentes comme safe
	private void markNeighorborsSafe() {
		for (Room rm: currentRoom.getNeighbors()) {
			if (rm != null) {
				rm.setSafe();
				rm.removeDanger('P');
				rm.removeDanger('W');
				rm.setPitRisk(0.0);
				rm.setWumpusRisk(0.0);
			}
		}
	}



//si la liste des cellules adjacentes n'est pas vide ,et si une cellule a un risque 
//de wumpus >=3 (Plus de trois cellules adjacentes à elle ont envoyee une perception W , et le danger est 'W ',alors elle contient certainement
//le wumpus , alors si le joueur a encore de fleche , on enleve le danger W et on remet le risque wqmpus à zero	
// on retourne un true , sinon un false




	private boolean shootWumpus() {
		Room front = currentRoom.getNeighbors()[dir];
		if (front != null && front.getWumpusRisk() >= 3 && front.getDangers().contains('W') && this.fleche > 0) {
			front.removeDanger('W');
			front.setWumpusRisk(0.0);
			return true;
		} else {
			return false;
		}
	}



//si la méthode shootwumpus retourne un true , on decide de tuer le wumpus(decideMove retourne S)
//si le champ gold est true , on decide de ramasser l'or , la methode decideMove retourne g
//on cherche la cellule avec le moins risque , si elle est c'est la cellule adjacente en face 
	//du joueur , on augmente son risque de 0,5 et on decide d'avancer 'f'

	//sinon on passe la cellule dans le paramètre de la fonction qui
	// cherche la meilleure  direction 



	public char decideMove() {
		
		if (this.gold) {
			return 'g';
		} else if (shootWumpus()) {
			return 's';
		}

		Room next = lookForLeastRiskyMove();
		Room roomFront = currentRoom.getNeighbors()[dir];

		if (next == roomFront) {
			roomFront.setVisitedRisk(roomFront.getVisitedRisk() + 0.5);
			return 'f';
		} else {
			return figureOutWhichDirectionToTurn(next); // 
		}
	}

	//on choisit la cellule adjacente ayant le plus petit risque , on l'envoit 
	//SINON ON RENVOIT NULL 

	
	private Room lookForLeastRiskyMove() {
		Room returnRoom = null;
		for (Room rm: currentRoom.getNeighbors()) {
			if (rm != null && returnRoom == null) {
				returnRoom = rm;
			} else if (rm != null && returnRoom.getTotalRisk() > rm.getTotalRisk()) {
				returnRoom = rm;
			}
		}
		return returnRoom;
	}





//observer le commentaire ci dessous

/*
	   3
 2     x    0
	   1
*/
// le joueur est suppose dans la case x 
//si il est en face de 0 (case 0), et
//si la meilleure cellule vers laquelle retourner est 3 , 
//on decider de tourner à gauche , ainsi par la même logique on comprendra 
// le reste des 'cases'





	private char figureOutWhichDirectionToTurn(Room bestMove) {
		ArrayList<Room> neighbors = new ArrayList<Room>(Arrays.asList(currentRoom.getNeighbors()));
	
		switch (dir) {
		case 0:

			if (bestMove == neighbors.get(3)) {
				return 'l';
			}
			break;
		case 1:
			if (bestMove == neighbors.get(0)) {
			return 'l';
			}
			break;
		case 2:
			
			if (bestMove == neighbors.get(1)){
				return 'l';
			}
				
			break;
		case 3:
			
			if (bestMove == neighbors.get(2)) {
			return 'l';
			}
			break;
			default:
			return 'r';
				
		}
	return 'r';
		
	}

	private Room lookForRiskyMove() {

		return null;
	}









//pour ne pas être bloqué dans une cellule 
//il faut mettre l'état des cellules adjacentes comme 
// UNVISITED , ainsi que leurs cellules adjacentes 
// en effet , si le joueur se déplace vers une cellule adjacente
// cette dernière doit avoir des cellule adjacentes unvisited ,
// pour que le joueur puisse sortir et se déplacer vers d'autres cellules
// si non , il reste bloqué  



	private void getUnStuck() {
	for (Room rm1: currentRoom.getNeighbors()) {
			if (rm1 != null) {
				rm1.unSetVisited();
				for (Room rm2: rm1.getNeighbors()) {
					if (rm2 != null) {
						rm2.unSetVisited();
					}
				}
			}
		}
	}



// si une cellule dans la liste des cellules adjacentes
// n'a pas de dangers , elle est safe

	private Room lookForSafeRoom() {
	for (Room rm: currentRoom.getNeighbors()) {
			if (rm != null && rm.getDangers().isEmpty()) {
			return rm;
			}
		}
		return null;
	}

//  chercher une cellule safe  unvisited :qui n'a pas de dangers , est dont sa methode  getVisited retourne false

	private Room lookForSafeUnVisitedRoom() {
		for (Room rm: currentRoom.getNeighbors()) {
			if (rm != null && rm.getDangers().isEmpty() && !rm.getVisited()) {
			return rm;
			}
		}
		return null;
	}

	
// Si la cellule a des cellules adjacentes , le joueur peur avancer
// s'il avance dans une cellule contenat
// un puit ou le wumpus , il mort


	public boolean avancer(){
		Room next = currentRoom.getNeighbors()[dir]; //
		if(next==null){
			return false;
		}
		else{
			currentRoom = next;
			currentRoom.setHints();
			if (currentRoom.hasDragon() || currentRoom.isPit())
				alive = false;
		}
		return true;
	}

	public void toutnerGauche(){
		dir = dir==0?3:dir-1;
	}

	public void tournerDroit(){
		dir = dir==3?0:dir+1;
	}

	public Room getCurrentRoom(){
		return currentRoom;
	}

	public boolean hasGold(){
		return gold;
	}
// sii le joueur ramasse l'or , on met à jour le champ gold
	public boolean grabGold(){
		gold = currentRoom.grabGold();
		return gold;
	}

	public boolean isDragonKiller(){
		return dragonKiller;
	}
//si le joueur tue le wumpus , on remet à jour le wumpus

	public void resetDragonKiller(){
		dragonKiller = false;
	}

	public boolean isAlive(){
		return alive;
	}

	public boolean hasArrow(){
		if (fleche > 0)
			return true;
		return false;
	}
// SI LE JOUEUR a une fleche ou deux , et il tue le wumpus le nombre de

// fleches se décrémente , et on on remet le champ dragonKiller à true

	public void shoot(){
		if(fleche==0)
			return;
		fleche --;
		Room r = currentRoom;
		while(r!=null){
			if(r.hasDragon()){
				r.killDragon();
				dragonKiller = true;
				return;
			}
			r = r.getNeighbors()[dir];
		}
	}

	public void quit(){
		alive = false;
	}

	public String getDirectionString(){
		return Board.DIRS[dir];
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void NewScreen(int ordre) {
		//
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				game Game = new game(ordre); 
				Game.setSize(943, 667); 
			}
		});
	}
}

