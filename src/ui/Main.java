package ui;

import model.Menu;

public class Main {
	private static Menu game;
	
	public static void main(String[] args) {
		/**in = new Scanner(System.in);
		game = new Game();
		start();**/
		
		Menu g = new Menu();
		g.createBoard(5, 5);
		g.setSnakesAndLadders(2, 2);
		g.setPlayer("@#$");
		g.showEnum();
		System.out.println("");
		g.showActual();
		g.startGame();
		
	}
	
}
