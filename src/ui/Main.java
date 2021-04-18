package ui;

import model.Board;

public class Main {
	public static void main(String[] args) {
		Board n = new Board(5,5);
		n.createBoard();
		n.recursiveRows(0);
	}
}
