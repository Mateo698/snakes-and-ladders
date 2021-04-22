package model;


public class Game {
	private Score scoresTree;
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
	
	public void showBoard() {
		board.show();
	}

	public Score getScoresTree() {
		return scoresTree;
	}

	public void setScoresTree(Score scoresTree) {
		this.scoresTree = scoresTree;
	}
}
