package model;

import java.util.ArrayList;

public class Game {
	private ArrayList<Score> scores;
	private Board board;
	
	public Game() {
		
	}
	
	public void createLadders(int ladders) {
		board.createLadders(ladders);
	}
	
	public void createSnakes(int snakes) {
		board.createSnakes(snakes);
	}
	
	public void setPlayers(String pl) {
		board.setPlayers(pl);
	}
	
	public void createBoard(int x,int y) {
		board = new Board(x,y);
	}
}
