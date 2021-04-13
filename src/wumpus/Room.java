package wumpus;

import java.awt.List;
import java.util.ArrayList;

import wumpus.game.Board;



public class Room {
	public static final int ROWS = Board.ROWS;
	private int x;
	private int y;
	private Room[] neighbors;
	private boolean pit;
	private boolean dragon;
	private boolean gold;
	private boolean hintShown;
	private boolean chocAir = false;
	private boolean quitte = true;
	private String hints;
	private ArrayList<Character> dangers = new ArrayList<Character>();
	private boolean isSafe = false;
	private boolean visited = false;
	private double wumpusRisk;
	private double pitRisk;
	private double visitedRisk;

	public Room (int x, int y) {
		this.x = x;
		this.y = y;
		this.pit = false;
		this.dragon = false;
		this.gold = false;
		neighbors = new Room[4];
		this.hintShown = false;
		this.hints = "";
		this.isSafe = false;
		this.wumpusRisk = 0;
		this.pitRisk = 0;
		this.visitedRisk = 0;
	}
	
	
	public double getWumpusRisk() {
		return this.wumpusRisk;
	}
	
	
	public void setWumpusRisk(double value) {
		this.wumpusRisk = value;
	}
	
	
	public double getPitRisk() {
		return this.pitRisk;
	}
	
	
	public void setPitRisk (double value) {
		this.pitRisk = value;
	}
	
	
	public double getVisitedRisk() {
		return this.visitedRisk;
	}
	
	public void setVisitedRisk (double value) {
		this.visitedRisk = value;
	}
	
	public double getTotalRisk() {
		return this.wumpusRisk + this.pitRisk + this.visitedRisk;
	}
	
	
	public boolean getVisited() {
		return this.visited;
	}
	
	
	public void setVisited() {
		this.visited = true;
	}
	
	
	public void unSetVisited() {
		this.visited = false;
	}
	
	
	public boolean getSafe() {
		return this.isSafe;
	}
	
	
	public void setSafe() {
		this.isSafe = true;
	}




	public void setBump(){

		this.chocAir = true;
	}



	public void setQuitte(){

		this.quitte = true;
	}



	// ajouter un danger à la liste des dangers d'une cellule
	
	
	public boolean setDangers (char dangers) {
		if (!this.dangers.contains(dangers)) {
			this.dangers.add(dangers);
			return true;
		} else
			return false;
	}
	// supprimer un danger

	public void removeDanger (char danger) {
		if (this.dangers.contains(danger)) {
//		
			this.dangers.clear();
		}
	}
	
	
	public ArrayList<Character> getDangers() {
		return this.dangers;
	}

		//mettre un puit
	public void makePit(){
		this.pit = true;
	}

	
	public boolean isPit(){
		return pit;
	}

	// mettre le wampus= (champ dragon=true)
	public void spawnDragon(){
		this.dragon = true;
	}

	// tuer dragon = (dragon=false)
	public void killDragon(){
		this.dragon = false;
	}

	// si la cellule contient le wampus

	public boolean hasDragon(){
		return this.dragon;
	}
// mettre l'or	
	
	public void depositGold(){
		this.gold = true;
	}


	


// ramasser l'or
	
	public boolean grabGold(){
		if(this.gold==false)
			return false;
		this.gold = false;
		return true;
	}

	
	public boolean hasGold(){
		return this.gold;
	}

//initailiser les cellules adjacentes		
	public void initNeighbors(Room[][] rooms){
		int len = rooms.length;//assume board is square
		neighbors[Board.WEST] = y==0?null:rooms[x][y-1];
		neighbors[Board.EAST] = y==len-1?null:rooms[x][y+1];
		neighbors[Board.NORTH] = x==0?null:rooms[x-1][y];
		neighbors[Board.SOUTH] = x==len-1?null:rooms[x+1][y];
	}

	// get les cellules adjacentes	
	public Room[] getNeighbors(){
		return neighbors;
	}

//liste des perceptions selon l'etat de la cellule et celui des cellules adjacentes


	public ArrayList<Character> perceptions(){
		ArrayList<Character> perceptions = new ArrayList<Character>();
		hints = "";
		boolean pitAir = false;
		boolean stenchAir = false;
		
		for (int i = 0; i < neighbors.length; i++){
			if(neighbors[i]!=null){
				if(neighbors[i].hasDragon())
					stenchAir = true;
				if(neighbors[i].isPit()){ 
					pitAir = true;
				}
			}
		}

		if (chocAir) 
			//			
			perceptions.add('C');
		
		if (pitAir)
//		
			perceptions.add('B');
		if (stenchAir)
//		
			perceptions.add('S');
		if (hasDragon())
//		
			perceptions.add('W');
		if (isPit())
//			
			perceptions.add('P');
		if (hasGold())
//		
			perceptions.add('G');

		if (quitte)
//		
			perceptions.add('T');

//	
		return perceptions;
	}

	public void setHints(){
		hintShown = true;
	}

	public boolean isShown(){
		return hintShown;
	}


	public int getLocation(){
		
		return ROWS*x+y;
	}
	
	public Room getRoom() {
		
		return this;
	}

	
}


