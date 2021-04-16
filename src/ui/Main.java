package ui;

import model.Board;

public class Main {
	public static void main(String[] args) {
		Board n = new Board(3,3);
		n.createBoard();
		n.showBoards();
	}
}
